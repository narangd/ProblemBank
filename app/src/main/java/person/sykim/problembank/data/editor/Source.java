package person.sykim.problembank.data.editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orm.SugarRecord;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import person.sykim.problembank.data.editor.constant.Constant;
import person.sykim.problembank.data.editor.execute.Execute;
import person.sykim.problembank.data.editor.json.ConstantSerializer;
import person.sykim.problembank.data.editor.json.ExecuteSerializer;

@EqualsAndHashCode(callSuper = false)
@Data
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
//@AllArgsConstructor
public class Source extends SugarRecord {
//    List<Group> groups = new ArrayList<>();
    String name;
//    @Builder.Default
    String json;
    Date createTime = new Date();
    Date updateTime = new Date();

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Constant.class, new ConstantSerializer())
                .registerTypeAdapter(Execute.class, new ExecuteSerializer())
                .create();
    }

    public static Gson getGsonPretty() {
        return new GsonBuilder()
                .registerTypeAdapter(Constant.class, new ConstantSerializer())
                .registerTypeAdapter(Execute.class, new ExecuteSerializer())
                .setPrettyPrinting()
                .create();
    }
}
