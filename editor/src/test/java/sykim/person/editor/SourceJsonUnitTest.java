package sykim.person.editor;

import com.google.gson.Gson;

import org.junit.Test;

import sykim.person.editor.constant.ConstantText;
import sykim.person.editor.constant.ConstantType;
import sykim.person.editor.execute.MakeVariable;
import sykim.person.editor.execute.PrintConsole;

import static org.junit.Assert.assertEquals;


public class SourceJsonUnitTest {

    @Test
    public void sourceParse() {
        Function function = new Function("main");
        function.add(new MakeVariable(ConstantType.TEXT, "abc", "test"));
        function.add(new PrintConsole(new ConstantText("console test text")));

        Gson gson = Source.getGsonPretty();
        String json = gson.toJson(function);
        System.out.println(function.toString());
        System.out.println(json);

        Function parsedFunction = gson.fromJson(json, Function.class);
        String parsedJson = gson.toJson(parsedFunction);
        System.out.println(parsedFunction.toString());

        assertEquals(parsedJson, json, parsedJson);
        assertEquals(parsedJson, function, parsedFunction);
    }
}
