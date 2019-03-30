package person.sykim.problembank;

import android.app.Application;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.HashMap;
import java.util.Map;

import person.sykim.problembank.data.bank.ProblemBank;
import person.sykim.problembank.data.User;

public class MyApplication extends Application {
    public Map<String, ProblemBank> bank = new HashMap<>();
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
