package person.sykim.problembank;

import android.app.Application;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

import person.sykim.problembank.data.ProblemBank;
import person.sykim.problembank.data.User;

public class MyApplication extends Application {
//    List<ProblemBank>
    public ProblemBank baekjoon;
    public User user;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }
}
