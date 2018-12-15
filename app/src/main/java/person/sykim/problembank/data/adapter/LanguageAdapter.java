package person.sykim.problembank.data.adapter;

import person.sykim.problembank.data.editor.SourceLine;

public abstract class LanguageAdapter<T> {
    public abstract SourceLine begin(T t);
    public abstract SourceLine body(T t, SourceLineList lines);
    public abstract SourceLine end(T t);

    public void run(SourceLineList list, T t) {
        list.add(begin(t));
        list.add(body(t, list));
        list.add(end(t));
    }
}
