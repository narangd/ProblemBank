package sykim.person.editor;

import org.junit.Test;

import sykim.person.editor.constant.ConstantText;
import sykim.person.editor.execute.PrintConsole;

import static junit.framework.TestCase.assertEquals;

public class PrintConsoleTest {

    @Test
    public void printTest() {
        ConstantText text = new ConstantText("Jack");
        PrintConsole console = new PrintConsole(text);
        console.add(new ConstantText(" is awesome"));

        assertEquals(console.makePrintText(), "Jack is awesome");
    }
}
