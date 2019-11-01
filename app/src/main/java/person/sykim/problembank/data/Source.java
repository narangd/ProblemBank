package person.sykim.problembank.data;

import android.os.Debug;
import android.util.Log;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import sykim.person.editor.Function;
import sykim.person.editor.SourceJson;
import sykim.person.editor.constant.ConstantText;
import sykim.person.editor.constant.ConstantType;
import sykim.person.editor.execute.MakeVariable;
import sykim.person.editor.execute.PrintConsole;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Source extends SugarRecord {
    private static final String TAG = "Source";
    String name;
    String json;
    Date createTime = new Date();
    Date updateTime;
    boolean sync = false;

    public static Source findDefault() {
        List<Source> list = Source.find(Source.class, "name=?", new String[]{"first"},
                null, "update_time DESC", "1");
        Log.d(TAG, "findDefault: "+list.size());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    public static Source saveDefault() {
        Source source = new Source();
        source.setName("first");

        // (temp) custom function
        Function function = new Function("main");
        function.add(new MakeVariable(ConstantType.INTEGER, "abc", "111"));
        function.add(new PrintConsole(new ConstantText("console test text")));

        String json = SourceJson.getGson().toJson(function);
        source.setJson(json);

        source.setId(source.save());
        return source;
    }
}
