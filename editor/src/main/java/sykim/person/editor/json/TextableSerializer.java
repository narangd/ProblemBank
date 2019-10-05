package sykim.person.editor.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import sykim.person.editor.Variable;
import sykim.person.editor.constant.ConstantText;
import sykim.person.editor.constant.ConstantType;
import sykim.person.editor.constant.Textable;

public class TextableSerializer implements JsonDeserializer<Textable>, JsonSerializer<Textable> {

    @Override
    public Textable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        if (object.get("name") != null) {
            return context.deserialize(json, Variable.class);
        } else {
            switch (ConstantType.parse(object.get("type").getAsString())) {
                case TEXT:
                    return context.deserialize(json, ConstantText.class);
//            case TEXT:
//                return context.deserialize(json, ConstantText.class);
                default:
                    return null;
            }
        }
    }

    @Override
    public JsonElement serialize(Textable src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }
}