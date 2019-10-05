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

import butterknife.BindView;
import butterknife.ButterKnife;
import person.sykim.problembank.R;
import sykim.person.editor.Source;


/**
 *
 */
public class SourceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = SourceAdapter.class.getSimpleName();

    private ArrayList<Source> sources = new ArrayList<>();

    public SourceAdapter () {
        Source source = new Source();
//        source.setPrev("public class Test {\n" +
//                "  public static void main(String[] args) {\n" +
//                "    System.out.println(\"Hello Programing World\");\n" +
//                "  }\n" +
//                "}\n");
        sources.add(source);
        sources.add(source);
        sources.add(source);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.holder_source, parent, false);
        return new SourceHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SourceHolder sourceHolder  = (SourceHolder) holder;
        Source source = sources.get(position);
//        sourceHolder.sourcePreviewTextView.setText(source.getPrev());
        sourceHolder.setOnClickListener((view) -> {});
        Log.i(TAG, "onBindViewHolder "+position);
//        if (holder instanceof SourceHolder) {
//        }
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

//    public void setSource(Source source) {
//        this.lines = new LanguageConverter().toJavaSource(source);
//    }

//    public interface OnVariableClickListener {
//        void onClick(Variable variable, int position);
//    }

    static class SourceHolder extends RecyclerView.ViewHolder {
        View root;
        @BindView(R.id.source_title)
        TextView titleTextView;
        @BindView(R.id.source_preview)
        TextView sourcePreviewTextView;

        SourceHolder(View root) {
            super(root);
            this.root = root;
            ButterKnife.bind(this, root);
        }

        void setOnClickListener(View.OnClickListener onClickListener) {
            root.setOnClickListener(onClickListener);
        }
    }
}
