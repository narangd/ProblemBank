package person.sykim.problembank.data.adapter.java;

//public class JavaFunctionConverter extends LanguageAdapter<Function> {
//    private JavaVariableConverter variableConverter = new JavaVariableConverter();
//    private JavaExecuteConverter executeConverter = new JavaExecuteConverter();
//
//    @Override
//    public SourceLine begin(Function function) {
//        return new SourceLine("  "+function.getReturnType()+" "+function.getName()+"() {");
//    }
//
//    @Override
//    public SourceLine body(Function function, SourceLineList list) {
//        for (Variable variable : function.getVariables()) {
//            variableConverter.run(list, variable);
//        }
//        for (Execute execute : function.getExecutes()) {
//            executeConverter.run(list, execute);
//        }
//        return null;
//    }
//
//    @Override
//    public SourceLine end(Function function) {
//        return new SourceLine("  }");
//    }
//}
