package person.sykim.problembank.data;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Collections;
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
    @Unique
    String name;
    String json;
    Date createTime = new Date();
    Date updateTime;
    boolean sync = false;

    /**
     * 마지막에 수정한 소스 로드.
     * @return 마지막에 수정한 소스
     */
    public static Source findLastUpdated() {
        List<Source> list = Source.find(Source.class, null, null,
                null, "update_time DESC", "1");
        Log.d(TAG, "findLastUpdated: "+list.size());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 초기 소스 데이터 생성
     * @return 초기 소스 데이터
     */
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

    public static boolean existName(String name) {
        return count(Source.class, "name = ?", new String[]{name}) > 0;
    }
}
