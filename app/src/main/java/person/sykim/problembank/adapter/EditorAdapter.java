package person.sykim.problembank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import person.sykim.problembank.R;
import person.sykim.problembank.data.editor.Source;


/**
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

//    public String source = "public class Source {\n" +
//            "    public static void main(String[] args) {\n" +
//            "        System.out.println(\"Hello World\");\n" +
//            "    }\n" +
//            "}";
//    String[] lines = source.split("\n");
    Source source;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LineHolder lineHolder = (LineHolder) holder;
        String line = lines[position];
        lineHolder.lineTextView.setText(line);
        lineHolder.setOnClickListener((view) -> {});
        Log.i(TAG, "onBindViewHolder line["+position+":"+line);
//        if (holder instanceof LineHolder) {
//        }
    }

    @Override
    public int getItemCount() {
        return lines.length;
    }

    public void setSource(Source source) {
        this.source = source;
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
