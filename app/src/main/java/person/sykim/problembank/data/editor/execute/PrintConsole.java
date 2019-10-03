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
import person.sykim.problembank.data.editor.constant.Constant;
import person.sykim.problembank.data.editor.constant.ConstantText;
import person.sykim.problembank.data.editor.constant.Textable;

@Data
@EqualsAndHashCode(callSuper = true)
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrintConsole extends ExecuteList<Constant> {

    private boolean newLine = false;

    public PrintConsole() {
        super(ExecuteType.PRINT_CONSOLE);
    }

    public PrintConsole(ConstantText text) {
        this();
        add(text);
    }

    @Override
    public void onExecute() {
        // console.print(makePrintText());
        //
    }

    // todo 데이터 조회시 Variable 처리
    //  Constant는 그대로 가져와도 되나,
    //  Variable은 VariableManager에서 조회하여 반환하여야함.
    public String makePrintText() {
        StringBuilder builder = new StringBuilder();
        for (Textable textable : this) {
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
