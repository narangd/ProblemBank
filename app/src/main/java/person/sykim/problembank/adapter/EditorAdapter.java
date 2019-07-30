package person.sykim.problembank.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import person.sykim.problembank.R;
import person.sykim.problembank.data.editor.Display;
import person.sykim.problembank.data.editor.ExecuteListener;
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
    public static final int ViewType_Display = 30;
    public static final int ViewType_Scanner = 40;
    public static final int ViewType_Calculate = 50;

//    private SourceLineList lines = new SourceLineList();
    ArrayList<ExecuteListener> list = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.holder_source_line, parent, false);
        return new LineHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LineHolder lineHolder = (LineHolder) holder;
        ExecuteListener listener = list.get(position);
        lineHolder.lineTextView.setText(listener.toString());
        lineHolder.setOnClickListener((view) -> {});
        Log.i(TAG, "onBindViewHolder line["+position+":"+listener);
//        if (holder instanceof LineHolder) {
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setSource(Source source) {
        list.add(Display.builder()
                .build());
//        this.list = new LanguageConverter().toJavaSource(source);
    }

//    public interface OnVariableClickListener {
//        void onClick(Variable variable, int position);
//    }

    static class LineHolder extends RecyclerView.ViewHolder {
        TextView lineTextView;

        LineHolder(View root) {
            super(root);
            this.lineTextView = (TextView) root;
        }

        void setOnClickListener(View.OnClickListener onClickListener) {
            lineTextView.setOnClickListener(onClickListener);
        }
    }
}
