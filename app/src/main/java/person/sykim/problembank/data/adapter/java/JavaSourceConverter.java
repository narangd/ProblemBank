package person.sykim.problembank.data.adapter.java;

import person.sykim.problembank.data.adapter.LanguageAdapter;
import person.sykim.problembank.data.adapter.SourceLineList;
import person.sykim.problembank.data.editor.Source;
import person.sykim.problembank.data.editor.SourceLine;

public class JavaSourceConverter extends LanguageAdapter<Source> {
//    private JavaMainConverter mainConverter = new JavaMainConverter();
//    private JavaGroupConverter groupConverter = new JavaGroupConverter();

    @Override
    public SourceLine begin(Source source) {
        return new SourceLine("public class Main {");
    }

    @Override
    public SourceLine body(Source source, SourceLineList list) {
//        mainConverter.run(list, source.getMain());
//
//        for (Group group : source.getGroups()) {
//            groupConverter.run(list, group);
//        }
        return null;
    }

    @Override
    public SourceLine end(Source source) {
        return new SourceLine("}");
    }
}
