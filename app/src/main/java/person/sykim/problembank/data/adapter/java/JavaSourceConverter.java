package person.sykim.problembank.data.adapter.java;

import person.sykim.problembank.data.adapter.LanguageAdapter;
import person.sykim.problembank.data.adapter.SourceLineList;
import sykim.person.editor.SourceJson;
import sykim.person.editor.SourceLine;

public class JavaSourceConverter extends LanguageAdapter<SourceJson> {
//    private JavaMainConverter mainConverter = new JavaMainConverter();
//    private JavaGroupConverter groupConverter = new JavaGroupConverter();

    @Override
    public SourceLine begin(SourceJson source) {
        return new SourceLine("public class Main {");
    }

    @Override
    public SourceLine body(SourceJson source, SourceLineList list) {
//        mainConverter.run(list, source.getMain());
//
//        for (Group group : source.getGroups()) {
//            groupConverter.run(list, group);
//        }
        return null;
    }

    @Override
    public SourceLine end(SourceJson source) {
        return new SourceLine("}");
    }
}
