package sykim.person.editor.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import sykim.person.editor.constant.Constant;
import sykim.person.editor.constant.ConstantBoolean;
import sykim.person.editor.constant.ConstantDecimal;
import sykim.person.editor.constant.ConstantInteger;
import sykim.person.editor.constant.ConstantText;
import sykim.person.editor.constant.ConstantType;

/**
 * 텍스트, 숫자, 실수, 불린 에 대한
 */
public class ConstantSerializer implements JsonDeserializer<Constant>, JsonSerializer<Constant> {

    @Override
    public Constant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String typeString = json.getAsJsonObject().get("type").getAsString();
        switch (ConstantType.parse(typeString)) {
            case INTEGER:
                return context.deserialize(json, ConstantInteger.class);
            case DECIMAL:
                return context.deserialize(json, ConstantDecimal.class);
            case BOOLEAN:
                return context.deserialize(json, ConstantBoolean.class);
            default:
            case TEXT:
                return context.deserialize(json, ConstantText.class);
        }
    }

    @Override
    public JsonElement serialize(Constant src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }
}