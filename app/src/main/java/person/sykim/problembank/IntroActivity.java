package person.sykim.problembank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import person.sykim.problembank.data.User;
import person.sykim.problembank.data.bank.ProblemBank;

public class IntroActivity extends AppCompatActivity {

    private static final String TAG = "IntroActivity";
    private static final int RC_SIGN_IN = 123;

    MyApplication application;
    FirebaseAuth auth;
    DatabaseReference reference;

    @BindView(R.id.loading_text)
    TextView loadingText;

    private boolean start = true;

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
            signinFirebase();
        }
    }

    void signinFirebase() {
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

        syncBanks.run();
    }


    Runnable syncBanks = () -> {
        runOnUiThread(() -> loadingText.setText("동기화 1단계 진행중..."));
        reference.child("banks")
                .addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bankSnapshot : dataSnapshot.getChildren()) {
                    String key = bankSnapshot.getKey();
                    ProblemBank problemBank = bankSnapshot.getValue(ProblemBank.class);
                    Log.i(TAG, "banks onDataChange: "+ key+", "+problemBank);

                    if (key != null && problemBank != null) {
                        problemBank.key = key;
                        application.bank.put(key, problemBank);
                    } else {
                        Log.e(TAG, "banks onDataChange: baekjoon 은행을 로드하지 못했습니다");
                        Log.e(TAG, "banks onDataChange: "+key);
                        Log.e(TAG, "banks onDataChange: "+problemBank);
                    }
                }
                dataSnapshot.getRef().removeEventListener(this);

                syncUser.run();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "banks onCancelled: "+databaseError.getMessage() +": "+databaseError.getDetails());

                syncUser.run();
            }
        });
    };
    Runnable syncUser = () -> {
        runOnUiThread(() -> loadingText.setText("동기화 2단계 진행중..."));
        reference.child("users")
                .child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        Log.d(TAG, "users onDataChange: "+user);
                        application.user = user;
                        if (user == null) {
                            Log.d(TAG, "users onDataChange: user is null");
                            user = new User();
                            user.setName(auth.getCurrentUser().getDisplayName());
                            dataSnapshot.getRef().setValue(user);
                        } else {
                            Log.d(TAG, "users onDataChange: user found");
                        }
                        dataSnapshot.getRef().removeEventListener(this);

                        startMain.run();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "users onCancelled: "+databaseError);

                        startMain.run();
                    }
                });
    };
    Runnable startMain = () -> {
        loadingText.setText("앱을 시작합니다");

        if (start) {
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
                signinFirebase();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        start = false;
    }
}
