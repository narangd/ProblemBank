package person.sykim.problembank.data.editor.execute;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import person.sykim.problembank.R;
import person.sykim.problembank.data.editor.AbstractHolder;

public class MakeVariable implements ExecuteListener {

    @Override
    public void onExecute() {

    }

    String makePrintText() {
        return "test";
    }

    public static class View extends AbstractHolder<PrintConsole> {
        @BindView(R.id.text_view)
        TextView textView;

        public View(@NonNull ViewGroup parent) {
            super(parent, R.layout.holder_editor_console);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void bind(PrintConsole printConsole) {
            textView.setText(printConsole.makePrintText());
        }
    }
}
