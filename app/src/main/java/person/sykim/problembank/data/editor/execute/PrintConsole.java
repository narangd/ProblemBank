package person.sykim.problembank.data.editor.execute;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import person.sykim.problembank.R;
import person.sykim.problembank.data.editor.AbstractHolder;
import person.sykim.problembank.data.editor.constant.ConstantText;
import person.sykim.problembank.data.editor.constant.Textable;

@Data
@EqualsAndHashCode(callSuper = true)
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrintConsole extends Execute {

    ArrayList<Textable> textableList = new ArrayList<>();

    public PrintConsole() {
        super(ExecuteType.PRINT_CONSOLE);
    }

    public PrintConsole(ConstantText text) {
        this();
        textableList.add(text);
    }

    @Override
    public void onExecute() {
        // console.print(makePrintText());
        //
    }

    public String makePrintText() {
        StringBuilder builder = new StringBuilder();
        for (Textable textable : textableList) {
            builder.append(textable.getText());
        }
        return builder.toString();
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
