package person.sykim.problembank.adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import sykim.person.editor.AbstractHolder;
import sykim.person.editor.Function;
import sykim.person.editor.SourceJson;
import sykim.person.editor.base.ListListener;
import sykim.person.editor.execute.Executable;
import sykim.person.editor.execute.MakeVariable;
import sykim.person.editor.execute.PrintConsole;

/**
 * Execute List
 * Created by jobs on 2017. 6. 7..
 */
@EqualsAndHashCode(callSuper = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ListListener<Executable> {
    private static final String TAG = EditorAdapter.class.getSimpleName();


    public static final int ViewType_Footer = 100;
    public static final int ViewType_PrintConsole = 10;
    public static final int ViewType_MakeVariable = 20;
//    public static final int ViewType_Function = 20;
//    public static final int ViewType_Scanner = 40;
//    public static final int ViewType_Calculate = 50;

    ArrayList<Executable> list = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ViewType_PrintConsole:
                return new PrintConsole.Holder(parent);
            case ViewType_MakeVariable:
                return new MakeVariable.Holder(parent);
        }
        throw new Resources.NotFoundException();
    }

    @Override
    public int getItemViewType(int position) {
        Executable executable = list.get(position);
        if (executable instanceof PrintConsole) {
            return ViewType_PrintConsole;
        } else if (executable instanceof MakeVariable) {
            return ViewType_MakeVariable;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        @SuppressWarnings("unchecked")
        AbstractHolder<Executable> holder = (AbstractHolder) viewHolder;
        Executable executable = list.get(position);
        holder.bind(executable);
        holder.itemView.setOnClickListener(v -> holder.onClick(this, position, executable));

        Log.i(TAG, "onBindViewHolder line[" + position + "]:" + executable);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setSource(SourceJson source) {
//        list.addAll();
    }

    public Function toFunction() {
        Function function = new Function("main");
        function.getList().addAll(list);
        return function;
    }


    /// start ListListener

    @Override
    public final void add(Executable executable) {
        list.add(executable);
        notifyItemInserted(list.size() - 1);
    }

    @Override
    public final void insert(int i, Executable executable) {
        list.add(i, executable);
        notifyItemInserted(i);
    }

    @Override
    public final void update(int i, Executable executable) {
        list.set(i, executable);
        notifyItemChanged(i);
    }

    @Override
    public final Executable delete(int i) {
        notifyItemRemoved(i);
        return list.remove(i);
    }

    /// end ListListener


}
