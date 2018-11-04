package person.sykim.problembank.data.adapter.java;

import java.util.List;

import person.sykim.problembank.data.adapter.LanguageAdapter;
import person.sykim.problembank.data.editor.Function;
import person.sykim.problembank.data.editor.SourceLine;

public class JavaFunctionConverter implements LanguageAdapter<Function> {
    @Override
    public SourceLine begin(Function function) {
        return new SourceLine("  void test() {");
    }

    @Override
    public SourceLine body(Function function, List<SourceLine> list) {
        return null;
    }

    @Override
    public SourceLine end(Function function) {
        return new SourceLine("  }");
    }
}
