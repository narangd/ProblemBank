package person.sykim.problembank.adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import person.sykim.problembank.data.editor.AbstractHolder;
import person.sykim.problembank.data.editor.execute.ExecuteListener;
import person.sykim.problembank.data.editor.execute.PrintConsole;
import person.sykim.problembank.data.editor.Source;

/**
 * ExecuteListener List
 * Created by jobs on 2017. 6. 7..
 */

public class EditorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = EditorAdapter.class.getSimpleName();


    public static final int ViewType_Footer = 100;
    public static final int ViewType_Variable = 10;
    public static final int ViewType_Function = 20;
    public static final int ViewType_Console = 30;
    public static final int ViewType_Scanner = 40;
    public static final int ViewType_Calculate = 50;

//    private SourceLineList lines = new SourceLineList();
    ArrayList<ExecuteListener> list = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ViewType_Console:
                return new PrintConsole.View(parent);
        }
        throw new Resources.NotFoundException();
    }

    @Override
    public int getItemViewType(int position) {
        return ViewType_Console;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        @SuppressWarnings("unchecked")
        AbstractHolder<ExecuteListener> adapter = (AbstractHolder) holder;
        ExecuteListener listener = list.get(position);
        adapter.bind(listener);

        Log.i(TAG, "onBindViewHolder line["+position+":"+listener);
//        if (holder instanceof LineHolder) {
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setSource(Source source) {
        list.add(PrintConsole.builder()
                .build());
//        this.list = new LanguageConverter().toJavaSource(source);
    }
}
