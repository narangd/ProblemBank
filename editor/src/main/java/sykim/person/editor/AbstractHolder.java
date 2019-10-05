package sykim.person.editor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class AbstractHolder<T> extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    public AbstractHolder(@NonNull ViewGroup parent, int resource) {
        super(LayoutInflater.from(parent.getContext())
                .inflate(resource, parent, false));
    }

    @Override
    public void onClick(View view) {}

    public abstract void bind(T t);

    public interface Adapter {

    }
}
