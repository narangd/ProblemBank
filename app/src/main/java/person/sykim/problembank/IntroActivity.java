package person.sykim.problembank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseUiException;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import person.sykim.problembank.data.ProblemBank;
import person.sykim.problembank.data.User;

public class IntroActivity extends AppCompatActivity {

    private static final String TAG = "IntroActivity";
    private static final int RC_SIGN_IN = 123;

    MyApplication application;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance()
            .getReference();

    @BindView(R.id.loading_text)
    TextView loadingText;

    private boolean start = true;
    private ArrayDeque<Runnable> queue = new ArrayDeque<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        application = (MyApplication) getApplication();

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            AsyncTask.execute(this::initFirebase);
        } else {
            openFirebaseLogin();
        }
    }

    void openFirebaseLogin() {
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.mipmap.ic_launcher_round)
                        .build(),
                RC_SIGN_IN);
    }

    void initFirebase() {

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Log.i(TAG, "initFirebase: id: " + user.getUid());
        }

        queue.add(syncBanks);
        queue.add(syncUser);
        queue.add(startMain);

        Runnable runnable = queue.poll();
        if (runnable != null) {
            AsyncTask.execute(runnable);
        }

    }


    Runnable syncBanks = () -> {
        runOnUiThread(() -> loadingText.setText("동기화 1단계 진행중..."));
        reference.child("banks").child("baekjoon").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProblemBank baekjoon = dataSnapshot.getValue(ProblemBank.class);
                Log.e(TAG, "onDataChange: "+ baekjoon);

                application.baekjoon = baekjoon;

                Runnable runnable = queue.poll();
                if (runnable != null) {
                    runOnUiThread(runnable);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: "+databaseError.getMessage() +": "+databaseError.getDetails());

                Runnable runnable = queue.poll();
                if (runnable != null) {
                    runOnUiThread(runnable);
                }
            }
        });
    };
    Runnable syncUser = () -> {
        runOnUiThread(() -> loadingText.setText("동기화 2단계 진행중..."));
        reference.child("users").child(auth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User u = dataSnapshot.getValue(User.class);
                        Log.d(TAG, "onDataChange: "+u);
                        application.user = u;
                        if (u == null) {
                            Log.d(TAG, "onDataChange: user is null");
                            dataSnapshot.getRef().child("name").setValue(auth.getCurrentUser().getDisplayName());
                        } else {
                            Log.d(TAG, "onDataChange: user found");
                            if (u.getKey() != null) {
                                dataSnapshot.getRef().removeEventListener(this);

                                Runnable runnable = queue.poll();
                                if (runnable != null) {
                                    runOnUiThread(runnable);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled: "+databaseError);

                        Runnable runnable = queue.poll();
                        if (runnable != null) {
                            runOnUiThread(runnable);
                        }
                    }
                });
    };
    Runnable startMain = () -> {
        loadingText.setText("앱을 시작합니다");

        if (start) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent main = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(main);
        }
        finish();
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                AsyncTask.execute(this::initFirebase);
            } else {
                if (response != null && response.getError() != null) {
                    FirebaseUiException exception = response.getError();
                    FirebaseAnalytics.getInstance(this)
                            .setUserProperty("FirebaseUI Login Error", exception.getMessage()+":"+exception.getErrorCode());
                }
                openFirebaseLogin();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        start = false;
    }
}
