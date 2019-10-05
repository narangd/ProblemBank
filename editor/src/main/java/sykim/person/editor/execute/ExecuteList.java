package sykim.person.editor.execute;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ExecuteList<T> implements Executable/*, List<T>*/, Iterable<T> {
    // List<T>를 사용시 Gson에서 {}가 아닌 []로 인식하는 문제가 발생.
    ExecuteType type;
    protected ArrayList<T> list = new ArrayList<>();

    public ExecuteList(ExecuteType type) {
        this.type = type;
    }

//    @Override
    public int size() {
        return list.size();
    }

//    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

//    @Override
    public boolean contains(@Nullable Object o) {
        return list.contains(o);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

//    @Nullable
//    @Override
//    public Object[] toArray() {
//        return list.toArray();
//    }

//    @SuppressWarnings("SuspiciousToArrayCall")
//    @Override
//    public <T1> T1[] toArray(@Nullable T1[] t1s) {
//        return list.toArray(t1s);
//    }

//    @Override
    public boolean add(T t) {
        return list.add(t);
    }

//    @Override
    public boolean remove(@Nullable T t) {
        return list.remove(t);
    }

//    @Override
//    public boolean containsAll(@NonNull Collection<?> collection) {
//        return list.containsAll(collection);
//    }

//    @Override
//    public boolean addAll(@NonNull Collection<? extends T> collection) {
//        return list.addAll(collection);
//    }

//    @Override
//    public boolean addAll(int i, @NonNull Collection<? extends T> collection) {
//        return list.addAll(i, collection);
//    }

//    @Override
//    public boolean removeAll(@NonNull Collection<?> collection) {
//        return list.removeAll(collection);
//    }

//    @Override
//    public boolean retainAll(@NonNull Collection<?> collection) {
//        return list.retainAll(collection);
//    }

//    @Override
    public void clear() {
        list.clear();
    }

//    @Override
    public T get(int i) {
        return list.get(i);
    }

//    @Override
    public T set(int i, T t) {
        return list.set(i, t);
    }

//    @Override
    public void add(int i, T t) {
        list.add(i, t);
    }

//    @Override
    public T remove(int i) {
        return list.remove(i);
    }

//    @Override
    public int indexOf(@Nullable T t) {
        return list.indexOf(t);
    }

//    @Override
    public int lastIndexOf(@Nullable T t) {
        return list.lastIndexOf(t);
    }

//    @NonNull
//    @Override
//    public ListIterator<T> listIterator() {
//        return list.listIterator();
//    }
//
//    @NonNull
//    @Override
//    public ListIterator<T> listIterator(int i) {
//        return list.listIterator(i);
//    }

//    @NonNull
//    @Override
//    public List<T> subList(int i, int i1) {
//        return list.subList(i, i1);
//    }
}
