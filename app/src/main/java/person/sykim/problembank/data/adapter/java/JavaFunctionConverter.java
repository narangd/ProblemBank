package person.sykim.problembank.data.adapter.java;

import person.sykim.problembank.data.adapter.LanguageAdapter;
import person.sykim.problembank.data.adapter.SourceLineList;
import person.sykim.problembank.data.editor.Execute;
import person.sykim.problembank.data.editor.Function;
import person.sykim.problembank.data.editor.SourceLine;
import person.sykim.problembank.data.editor.Variable;

public class JavaFunctionConverter extends LanguageAdapter<Function> {
    private JavaVariableConverter variableConverter = new JavaVariableConverter();
    private JavaExecuteConverter executeConverter = new JavaExecuteConverter();

    @Override
    public SourceLine begin(Function function) {
        return new SourceLine("  "+function.getReturnType()+" "+function.getName()+"() {");
    }

    @Override
    public SourceLine body(Function function, SourceLineList list) {
        for (Variable variable : function.getVariables()) {
            variableConverter.run(list, variable);
        }
        for (Execute execute : function.getExecutes()) {
            executeConverter.run(list, execute);
        }
        return null;
    }

    @Override
    public SourceLine end(Function function) {
        return new SourceLine("  }");
    }
}
