package person.sykim.problembank.data.adapter.java;

import person.sykim.problembank.data.adapter.LanguageAdapter;
import person.sykim.problembank.data.adapter.SourceLineList;
import sykim.person.editor.SourceLine;
import sykim.person.editor.Variable;

public class JavaVariableConverter extends LanguageAdapter<Variable> {
    @Override
    public SourceLine begin(Variable variable) {
        return null;
    }

    @Override
    public SourceLine body(Variable variable, SourceLineList lines) {
        return null;
    }

    @Override
    public SourceLine end(Variable variable) {
        return null;
    }
}
