package person.sykim.problembank;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import person.sykim.problembank.data.Problem;
import person.sykim.problembank.data.ProblemBank;
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
