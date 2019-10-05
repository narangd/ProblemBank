package sykim.person.editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import sykim.person.editor.constant.Constant;
import sykim.person.editor.constant.Textable;
import sykim.person.editor.execute.Executable;
import sykim.person.editor.json.ConstantSerializer;
import sykim.person.editor.json.ExecuteSerializer;
import sykim.person.editor.json.TextableSerializer;

@EqualsAndHashCode(callSuper = false)
@Data
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
//@AllArgsConstructor
public class Source {
//    List<Group> groups = new ArrayList<>();
    String name;
//    @Builder.Default
    String json;
    Date createTime = new Date();
    Date updateTime = new Date();

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Constant.class, new ConstantSerializer())
                .registerTypeAdapter(Textable.class, new TextableSerializer())
                .registerTypeAdapter(Executable.class, new ExecuteSerializer())
                .create();
    }

    public static Gson getGsonPretty() {
        return new GsonBuilder()
                .registerTypeAdapter(Constant.class, new ConstantSerializer())
                .registerTypeAdapter(Textable.class, new TextableSerializer())
                .registerTypeAdapter(Executable.class, new ExecuteSerializer())
                .setPrettyPrinting()
                .create();
    }
}
