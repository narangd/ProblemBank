package sykim.person.editor;

import org.junit.Test;

import sykim.person.editor.constant.ConstantText;
import sykim.person.editor.execute.PrintConsole;

import static junit.framework.TestCase.assertEquals;

public class PrintConsoleTest {

    @Test
    public void printTest() {
        ConstantText text = new ConstantText("Jack");
        PrintConsole printConsole = new PrintConsole(text);
        printConsole.add(new ConstantText(" is awesome"));

        StringBuilder console = Program.getInstance().console;
        printConsole.makePrintText(console);
        assertEquals(console.toString(), "Jack is awesome");
    }
}
