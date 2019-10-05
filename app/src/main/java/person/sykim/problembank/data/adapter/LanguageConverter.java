package person.sykim.problembank.data.adapter;

import person.sykim.problembank.data.adapter.java.JavaSourceConverter;
import sykim.person.editor.Source;

public class LanguageConverter {
    public SourceLineList toJavaSource(Source source) {
        SourceLineList list = new SourceLineList();
        new JavaSourceConverter().run(list, source);
        return list;
    }
}
