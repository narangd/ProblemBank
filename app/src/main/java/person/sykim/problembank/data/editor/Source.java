package person.sykim.problembank.data.editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orm.SugarRecord;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import person.sykim.problembank.data.editor.constant.Constant;
import person.sykim.problembank.data.editor.execute.Execute;
import person.sykim.problembank.data.editor.json.ConstantSerializer;
import person.sykim.problembank.data.editor.json.ExecuteSerializer;

@Data
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Source extends SugarRecord<Source> {
//    List<Group> groups = new ArrayList<>();
    String name;
//    @Builder.Default
    Function main = new Function();
    LocalDateTime createTime;
    LocalDateTime updateTime;

    public void add(Execute execute) {
        main.getList().add(execute);
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Constant.class, new ConstantSerializer())
                .registerTypeAdapter(Execute.class, new ExecuteSerializer())
                .create();
    }
}
