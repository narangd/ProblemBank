package person.sykim.problembank.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import person.sykim.problembank.R;
import person.sykim.problembank.data.bank.Problem;


public class ProblemAdapter extends RecyclerView.Adapter {

    private static final String TAG = "ProblemAdapter";

    private static final int ViewType_Problem = 0;
    private static final int ViewType_LoadButton = 1;

    public static class ProblemHolder extends RecyclerView.ViewHolder {
        View root;
        @BindView(R.id.name_text_view)
        TextView titleView;
        @BindView(R.id.status_image_view)
        ImageView iconView;
        @BindColor(R.color.gray_300)
        int gray;
        ProblemHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);
            this.root = root;
        }
    }
//    private static class LoadHolder extends RecyclerView.ViewHolder {
//        View root;
//        Button loadButton;
//        public LoadHolder(View itemView) {
//            super(itemView);
//            root = itemView;
//            loadButton = itemView.findViewById(R.id.load_button);
//        }
//    }

    private List<Problem> problemList = new ArrayList<>();
    private OnProblemClickListener onProblemClickListener;
//    private OnLoadListener onLoadListener;

    public ProblemAdapter() {
        List<Problem> problemList = new ArrayList<>();
        for (int i=1; i<=10; i++) problemList.add(null);
        this.problemList.addAll(problemList);
    }

    public void reset(List<Problem> problemList) {
        this.problemList.clear();
        this.problemList.addAll(problemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root;
//        if (viewType == ViewType_Problem) {
        root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_problem, parent, false);
        ProblemHolder holder = new ProblemHolder(root);
        holder.iconView.setBackgroundColor(holder.gray);
        holder.titleView.setBackgroundColor(holder.gray);
        return holder;

//        } else {
//            root = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.holder_load, parent, false);
//            return new LoadHolder(root);
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof ProblemHolder) {
            final Problem problem = problemList.get(position);
            if (problem == null) return;
            ProblemHolder problemHolder = (ProblemHolder) holder;
            String text = problem.code + " : " + problem.title;
            problemHolder.titleView.setText(text);
            problemHolder.titleView.setBackground(null);
            problemHolder.iconView.setBackground(null);
            switch (problem.status) {
                case "success":
                    problemHolder.iconView.setImageResource(R.drawable.ic_check_circle);
                    break;
                case "failure":
                    problemHolder.iconView.setImageResource(R.drawable.ic_close_circle);
                    break;
                default:
                    problemHolder.iconView.setImageResource(R.drawable.ic_full_circle);
                    break;
            }
            problemHolder.root.setOnClickListener(v -> {
                if (onProblemClickListener != null) {
                    onProblemClickListener.onClick(problem);
                }
            });
//        } else if (holder instanceof LoadHolder) {
//            LoadHolder loadHolder = (LoadHolder) holder;
//            loadHolder.loadButton.setOnClickListener((View.OnClickListener) v -> System.out.println("View Load"));
//        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (position < problemList.size()) {
        return ViewType_Problem;
//        } else {
//            return ViewType_LoadButton;
//        }
    }

    @Override
    public int getItemCount() {
        return problemList.size() /* load button */;
    }



//    public void update(List<Problem> problemList) {
//        this.problemList = problemList;
//        notifyDataSetChanged();
//    }
//
//    public void insert(List<Problem> problemList) {
//        int prevIndex = this.problemList.size();
//        this.problemList.addAll(problemList);
//        notifyItemRangeInserted(prevIndex, problemList.size());
//    }

    public void setOnProblemClickListener(OnProblemClickListener onProblemClickListener) {
        this.onProblemClickListener = onProblemClickListener;
    }
//
//    public void setOnLoadListener(OnLoadListener onLoadListener) {
//        this.onLoadListener = onLoadListener;
//    }
//
    public interface OnProblemClickListener {
        void onClick(Problem problem);
    }
//
//    public interface OnLoadListener {
//        void onLoad();
//    }
}

