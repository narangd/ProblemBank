package sykim.person.editor.base;

public interface ListListener<T> {
    void add(T t);
    void insert(int i, T t);
    void update(int i, T t);
    T delete(int i);
}
