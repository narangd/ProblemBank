package person.sykim.problembank;

import com.google.gson.Gson;

import org.junit.Test;

import person.sykim.problembank.data.editor.Function;
import person.sykim.problembank.data.editor.Source;
import person.sykim.problembank.data.editor.constant.ConstantText;
import person.sykim.problembank.data.editor.constant.ConstantType;
import person.sykim.problembank.data.editor.execute.MakeVariable;
import person.sykim.problembank.data.editor.execute.PrintConsole;

import static org.junit.Assert.assertEquals;


public class SourceJsonUnitTest {

    @Test
    public void source() {
        Function function = new Function();
        function.add(new MakeVariable(ConstantType.TEXT, "abc", "test"));
        function.add(new PrintConsole(new ConstantText("console test text")));

        Gson gson = Source.getGson();
        String json = gson.toJson(function);

        Function parsedFunction = gson.fromJson(json, Function.class);

        System.out.println(function);
        System.out.println(json);
        assertEquals("Function Serialize Test", function, parsedFunction);
    }
}
