package person.sykim.problembank.data;

import person.sykim.problembank.data.editor.Execute;
import person.sykim.problembank.data.editor.Function;

public class Display extends Execute {
    public Display(String param) {
        super("System.out.print", param);
    }
}
