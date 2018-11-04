package person.sykim.problembank.data.adapter.java;

import java.util.List;

import person.sykim.problembank.data.adapter.LanguageAdapter;
import person.sykim.problembank.data.editor.Function;
import person.sykim.problembank.data.editor.Group;
import person.sykim.problembank.data.editor.Source;
import person.sykim.problembank.data.editor.SourceLine;

public class JavaSourceConverter implements LanguageAdapter<Source> {
    @Override
    public SourceLine begin(Source source) {
        return new SourceLine("public class Main {");
    }

    @Override
    public SourceLine body(Source source, List<SourceLine> list) {
        JavaMainConverter mainConverter = new JavaMainConverter();
        Function main = source.getMain();
        list.add(mainConverter.begin(main));
        list.add(mainConverter.body(main, list));
        list.add(mainConverter.end(main));

        for (Group group : source.getGroups()) {
//            list.add(converter.begin(source));
//            list.add(converter.body(source, list));
//            list.add(converter.end(source));
        }
        return null;
    }

    @Override
    public SourceLine end(Source source) {
        return new SourceLine("}");
    }
}
