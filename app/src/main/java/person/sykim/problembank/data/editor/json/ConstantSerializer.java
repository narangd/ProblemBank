package person.sykim.problembank.data.editor.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import person.sykim.problembank.data.editor.constant.Constant;
import person.sykim.problembank.data.editor.constant.ConstantText;
import person.sykim.problembank.data.editor.constant.ConstantType;

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