package person.sykim.problembank.data.adapter.java;

import person.sykim.problembank.data.adapter.LanguageAdapter;
import person.sykim.problembank.data.adapter.SourceLineList;
import person.sykim.problembank.data.editor.Execute;
import person.sykim.problembank.data.editor.SourceLine;

public class JavaExecuteConverter extends LanguageAdapter<Execute> {
    @Override
    public SourceLine begin(Execute variable) {
        return null;
    }

    @Override
    public SourceLine body(Execute variable, SourceLineList lines) {
        return null;
    }

    @Override
    public SourceLine end(Execute variable) {
        return null;
    }
}
