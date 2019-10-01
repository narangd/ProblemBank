package person.sykim.problembank.source;

import org.junit.Test;

import person.sykim.problembank.data.editor.constant.ConstantText;
import person.sykim.problembank.data.editor.execute.PrintConsole;

import static org.junit.Assert.assertEquals;

public class PrintConsoleTest {

    @Test
    public void printTest() {
        ConstantText text = new ConstantText("Jack");
        PrintConsole console = new PrintConsole(text);

        assertEquals(console.makePrintText(), "Jack");
    }
}
