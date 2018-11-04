package person.sykim.problembank.data.adapter;

import person.sykim.problembank.data.adapter.java.JavaSourceConverter;
import person.sykim.problembank.data.editor.Source;

public class LanguageConverter {
    public SourceLineList toJavaSource(Source source) {
        SourceLineList list = new SourceLineList();
        JavaSourceConverter converter = new JavaSourceConverter();
        list.add(converter.begin(source));
        list.add(converter.body(source, list));
        list.add(converter.end(source));
        return list;
    }
}
