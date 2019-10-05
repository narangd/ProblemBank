package sykim.person.editor.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import sykim.person.editor.Function;
import sykim.person.editor.execute.Executable;
import sykim.person.editor.execute.ExecuteType;
import sykim.person.editor.execute.MakeVariable;
import sykim.person.editor.execute.PrintConsole;

public class ExecuteSerializer implements JsonDeserializer<Executable>, JsonSerializer<Executable> {

    @Override
    public Executable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String typeString = json.getAsJsonObject().get("type").getAsString();
        ExecuteType executeType = ExecuteType.parse(typeString);
        if (executeType == null) {
            return null;
        }

        switch (executeType) {
            case MAKE_VARIABLE:
                return context.deserialize(json, MakeVariable.class);
            case PRINT_CONSOLE:
                return context.deserialize(json, PrintConsole.class);
            case FUNCTION:
                return context.deserialize(json, Function.class);
            default:
                return null;
        }
    }

    @Override
    public JsonElement serialize(Executable src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }
}