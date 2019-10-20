package sykim.person.editor.execute;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import sykim.person.editor.base.ListListener;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ExecuteList<T> implements Executable, ListListener<T>, Iterable<T> {
    // List<T>를 사용시 Gson에서 {}가 아닌 []로 인식하는 문제가 발생.
    ExecuteType type;
    ArrayList<T> list = new ArrayList<>();

    public ExecuteList(ExecuteType type) {
        this.type = type;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean contains(@Nullable Object o) {
        return list.contains(o);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    public void clear() {
        list.clear();
    }

    public T get(int i) {
        return list.get(i);
    }

    @Override
    public void add(T t) {
        list.add(t);
    }

    @Override
    public void insert(int i, T t) {
        list.add(i, t);
    }

    @Override
    public void update(int i, T t) {
        list.set(i, t);
    }

    @Override
    public T delete(int i) {
        return list.remove(i);
    }
}
