package sykim.person.editor;

import org.junit.Test;

import sykim.person.editor.constant.Constant;
import sykim.person.editor.constant.ConstantText;
import sykim.person.editor.constant.ConstantType;
import sykim.person.editor.execute.MakeVariable;
import sykim.person.editor.execute.PrintConsole;

import static junit.framework.TestCase.assertEquals;

public class PrintConsoleTest {

    @Test
    public void printTest() {
        final String name = "name";
        MakeVariable makeVariable = new MakeVariable(ConstantType.TEXT, name, "Jack");
        makeVariable.onExecute();

        System.out.println("----- memory status -----");
        Program program = Program.getInstance();
        for (String key : program.memory.iteratorVariable()) {
            Constant value = program.memory.getVariable(key);
            System.out.println(value.getType()+" "+key+" = "+value.getText());
        }
        System.out.println("----- memory status -----");
        System.out.println();

        PrintConsole printConsole = new PrintConsole();
        printConsole.add(program.memory.getVariable(name));
        printConsole.add(new ConstantText(" is awesome"));

        StringBuilder console = Program.getInstance().console;
        printConsole.makePrintText(console);

        String consoleText = console.toString();
        System.out.println(consoleText);
        assertEquals(consoleText, "Jack is awesome");
    }
}
