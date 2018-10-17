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

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import person.sykim.problembank.data.ProblemBank;

public class IntroActivity extends AppCompatActivity {

    private static final String TAG = "IntroActivity";
    private static final int RC_SIGN_IN = 123;

    MyApplication application;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @BindView(R.id.loading_text)
    TextView loadingText;

    private boolean start = true;

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

    public void openFirebaseLogin() {
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
        runOnUiThread(() -> loadingText.setText("Initialize firebase..."));

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Log.i(TAG, "initFirebase: id: " + user.getUid());
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        reference.child("banks").child("baekjoon").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProblemBank baekjoon = dataSnapshot.getValue(ProblemBank.class);
                Log.e(TAG, "onDataChange: "+ baekjoon);

                application.baekjoon = baekjoon;

                runOnUiThread(IntroActivity.this::startMain);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: "+databaseError.getMessage() +": "+databaseError.getDetails());

                runOnUiThread(IntroActivity.this::startMain);
            }
        });

    }

    public void startMain() {
        loadingText.setText("Starting ProblemBank...");

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
    }

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
