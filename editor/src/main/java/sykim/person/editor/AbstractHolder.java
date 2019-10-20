package sykim.person.editor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sykim.person.editor.base.ListListener;
import sykim.person.editor.execute.Executable;

public abstract class AbstractHolder<T extends Executable> extends RecyclerView.ViewHolder {

    public AbstractHolder(@NonNull ViewGroup parent, int resource) {
        super(LayoutInflater.from(parent.getContext())
                .inflate(resource, parent, false));
    }

    /**
     * holder 클릭되는 것 처리. 수정, 삭제를 실행한다.
     * @param listener
     * @param index
     * @param executable
     */
    public void onClick(ListListener<Executable> listener, int index, T executable) {
        executable.openEditor(itemView.getContext(), listener, index);
    }

    public abstract void bind(T t);

    public interface Adapter {

    }
}
