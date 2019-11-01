package sykim.person.editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import sykim.person.editor.constant.Constant;
import sykim.person.editor.constant.Textable;
import sykim.person.editor.execute.Executable;
import sykim.person.editor.json.ConstantSerializer;
import sykim.person.editor.json.ExecuteSerializer;
import sykim.person.editor.json.TextableSerializer;

public class SourceJson {

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
