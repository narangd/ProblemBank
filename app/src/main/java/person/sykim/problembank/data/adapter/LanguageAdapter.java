package person.sykim.problembank.data.adapter;

import java.util.List;

import person.sykim.problembank.data.editor.SourceLine;

public interface LanguageAdapter<T> {
    SourceLine begin(T t);
    SourceLine body(T t, List<SourceLine> lines);
    SourceLine end(T t);
}
