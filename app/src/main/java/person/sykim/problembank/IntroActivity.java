package person.sykim.problembank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseUiException;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
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
    FirebaseAuth auth;
    DatabaseReference reference;

    private ValueEventListener banksListener;

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
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance()
                .getReference();

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
        banksListener = reference.child("banks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bankSnapshot : dataSnapshot.getChildren()) {
                    String key = bankSnapshot.getKey();
                    ProblemBank problemBank = bankSnapshot.getValue(ProblemBank.class);
                    Log.i(TAG, "banks onDataChange: "+ key+", "+problemBank);

                    if (key != null && problemBank != null) {
                        application.bank.put(key, problemBank);
                    } else {
                        Log.e(TAG, "banks onDataChange: baekjoon 은행을 로드하지 못했습니다");
                        Log.e(TAG, "banks onDataChange: "+key);
                        Log.e(TAG, "banks onDataChange: "+problemBank);
                    }

                    Runnable runnable = queue.poll();
                    if (runnable != null) {
                        runOnUiThread(runnable);
                    }
                }
                dataSnapshot.getRef().removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "banks onCancelled: "+databaseError.getMessage() +": "+databaseError.getDetails());

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
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User u = dataSnapshot.getValue(User.class);
                        Log.d(TAG, "users onDataChange: "+u);
                        application.user = u;
                        if (u == null) {
                            Log.d(TAG, "users onDataChange: user is null");
                            u = new User();
                            u.setName(auth.getCurrentUser().getDisplayName());
                            dataSnapshot.getRef().setValue(u);
                        } else {
                            Log.d(TAG, "users onDataChange: user found");
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
                        Log.e(TAG, "users onCancelled: "+databaseError);

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
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            Intent main = new Intent(IntroActivity.this, MainActivity.class);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
    protected void onDestroy() {
        super.onDestroy();
        reference.removeEventListener(banksListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        start = false;
    }
}
