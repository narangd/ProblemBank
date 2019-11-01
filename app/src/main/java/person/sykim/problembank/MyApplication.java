package person.sykim.problembank;

import android.content.ContextWrapper;

import com.orm.SugarApp;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.HashMap;
import java.util.Map;

import person.sykim.problembank.data.User;
import person.sykim.problembank.data.bank.ProblemBank;

public class MyApplication extends SugarApp {
    private static final String TAG = MyApplication.class.getSimpleName();
    public Map<String, ProblemBank> bank = new HashMap<>();
    public User user;

    @Override
    public void onCreate() {
        super.onCreate();

//        List<Source> list = Source.listAll(Source.class);
//        Log.d(TAG, "saved sources "+list);
        /*
        no table 에러시 instance run 설정이 켜져있지 않는지 확인해야함.
         */


        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }


}
