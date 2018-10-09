package person.sykim.problembank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity extends AppCompatActivity {

    private static final String TAG = "IntroActivity";

    @BindView(R.id.loading_text) TextView loadingText;

    private long startTime = System.currentTimeMillis();
    private boolean start = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

        AsyncTask.execute(this::initFirebase);
    }

    void initFirebase() {
        runOnUiThread(() -> loadingText.setText("Initialize firebase..."));

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference();

//        reference.child("banks").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.i(TAG, "onDataChange: "+dataSnapshot.getValue());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.i(TAG, "onCancelled: "+databaseError.getMessage() +": "+databaseError.getDetails());
//            }
//        });

//        startMain();
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
