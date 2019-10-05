package sykim.person.editor.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONObject;

import java.lang.reflect.Type;

import sykim.person.editor.Variable;
import sykim.person.editor.constant.Constant;
import sykim.person.editor.constant.ConstantText;
import sykim.person.editor.constant.ConstantType;
import sykim.person.editor.constant.Textable;

/**
 * 텍스트, 숫자, 실수, 불린 에 대한
 */
public class ConstantSerializer implements JsonDeserializer<Constant>, JsonSerializer<Constant> {

    @Override
    public Constant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String typeString = json.getAsJsonObject().get("type").getAsString();
        switch (ConstantType.parse(typeString)) {
            case TEXT:
                return context.deserialize(json, ConstantText.class);
//            case TEXT:
//                return context.deserialize(json, ConstantText.class);
            default:
                return null;
        }
    }

    @Override
    public JsonElement serialize(Constant src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }
}