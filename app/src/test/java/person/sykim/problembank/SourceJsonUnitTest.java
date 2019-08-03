package person.sykim.problembank;

import com.google.gson.Gson;

import org.junit.Test;

import person.sykim.problembank.data.editor.Source;
import person.sykim.problembank.data.editor.constant.ConstantText;
import person.sykim.problembank.data.editor.constant.ConstantType;
import person.sykim.problembank.data.editor.execute.MakeVariable;
import person.sykim.problembank.data.editor.execute.PrintConsole;

import static org.junit.Assert.assertEquals;


public class SourceJsonUnitTest {

    @Test
    public void source() {
        Source source = new Source();
        source.add(new MakeVariable(ConstantType.TEXT, "abc", "test"));
        source.add(new PrintConsole(new ConstantText("console test text")));

        Gson gson = Source.getGson();
        String json = gson.toJson(source);

        Source parsedSource = gson.fromJson(json, Source.class);

        System.out.println(source);
        System.out.println(json);
        assertEquals("Source Serialize Test", source, parsedSource);
    }
}
