package person.sykim.problembank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import person.sykim.problembank.data.ProblemBank;

public class IntroActivity extends AppCompatActivity {

    private static final String TAG = "IntroActivity";

    MyApplication application;
    @BindView(R.id.loading_text) TextView loadingText;

    private boolean start = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        application = (MyApplication) getApplication();

        AsyncTask.execute(this::initFirebase);
    }

    void initFirebase() {
        runOnUiThread(() -> loadingText.setText("Initialize firebase..."));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        reference.child("banks").child("baekjoon").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProblemBank baekjoon = dataSnapshot.getValue(ProblemBank.class);
                Log.e(TAG, "onDataChange: "+ baekjoon);

                application.baekjoon = baekjoon;

                AsyncTask.execute(IntroActivity.this::printList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: "+databaseError.getMessage() +": "+databaseError.getDetails());
            }
        });

//        startMain();
    }

    public void printList() {
    }

    public void startMain() {
        runOnUiThread(() -> loadingText.setText("Starting ProblemBank..."));

        if (start) return; //

        new Handler().postDelayed(() -> {
            if (start) {
                Intent main = new Intent(IntroActivity.this, MainActivity.class);
//                    main.set
                startActivity(main);
            }
            finish();
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        start = false;
    }
}
