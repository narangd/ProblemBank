package person.sykim.problembank;

import android.content.ContextWrapper;
import android.util.Log;

import com.orm.SugarApp;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import person.sykim.problembank.data.User;
import person.sykim.problembank.data.bank.ProblemBank;
import person.sykim.problembank.data.editor.Function;
import person.sykim.problembank.data.editor.Source;
import person.sykim.problembank.data.editor.constant.ConstantText;
import person.sykim.problembank.data.editor.constant.ConstantType;
import person.sykim.problembank.data.editor.execute.MakeVariable;
import person.sykim.problembank.data.editor.execute.PrintConsole;

public class MyApplication extends SugarApp {
    private static final String TAG = MyApplication.class.getSimpleName();
    public Map<String, ProblemBank> bank = new HashMap<>();
    public User user;

    @Override
    public void onCreate() {
        super.onCreate();



        Function function = new Function();
        function.getList().add(new MakeVariable(ConstantType.TEXT, "abc", "test"));
        function.getList().add(new PrintConsole(new ConstantText("console test text")));
        Log.d(TAG, "create json "+Source.getGson().toJson(function));
        List<Source> list = Source.listAll(Source.class);
        Log.d(TAG, "saved sources "+list);

        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }
}
