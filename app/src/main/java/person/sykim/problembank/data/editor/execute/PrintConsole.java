package person.sykim.problembank.data.editor.execute;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import person.sykim.problembank.R;
import person.sykim.problembank.data.editor.AbstractHolder;
import person.sykim.problembank.data.editor.constant.ConstantText;

@Data
@EqualsAndHashCode(callSuper = true)
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrintConsole extends Execute {

//    @Builder.Default
    ConstantText text = new ConstantText("");

    public PrintConsole() {
        super(ExecuteType.PRINT_CONSOLE);
    }

    public PrintConsole(ConstantText text) {
        this();
        this.text = text;
    }

    @Override
    public void onExecute() {
        // console.print(makePrintText());
        //
    }

    String makePrintText() {
        return text.getValue();
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
