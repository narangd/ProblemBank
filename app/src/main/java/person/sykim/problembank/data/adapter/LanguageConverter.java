package person.sykim.problembank.data.adapter;

import person.sykim.problembank.data.adapter.java.JavaSourceConverter;
import person.sykim.problembank.data.editor.Source;

public class LanguageConverter {
    public SourceLineList toJavaSource(Source source) {
        SourceLineList list = new SourceLineList();
        new JavaSourceConverter().run(list, source);
        return list;
    }
}
