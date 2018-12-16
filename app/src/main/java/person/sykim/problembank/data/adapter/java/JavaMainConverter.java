package person.sykim.problembank.data.adapter.java;

import person.sykim.problembank.data.editor.Function;
import person.sykim.problembank.data.editor.SourceLine;

public class JavaMainConverter extends JavaFunctionConverter {
    @Override
    public SourceLine begin(Function function) {
        return new SourceLine("  public static void main(String[] args) {");
    }

    @Override
    public SourceLine end(Function function) {
        return new SourceLine("  }");
    }
}
