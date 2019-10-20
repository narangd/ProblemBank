package sykim.person.editor.execute;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import sykim.person.editor.AbstractHolder;
import sykim.person.editor.Program;
import sykim.person.editor.R;
import sykim.person.editor.R2;
import sykim.person.editor.base.ListListener;
import sykim.person.editor.constant.ConstantText;
import sykim.person.editor.constant.Textable;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrintConsole extends ExecuteList<Textable> {

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
        makePrintText( Program.getInstance().console );
    }

    @Override
    public void openEditor(Context context, ListListener<Executable> listener, int index) {

    }

    // todo 데이터 조회시 Variable 처리
    //  Constant는 그대로 가져와도 되나,
    //  Variable은 VariableManager에서 조회하여 반환하여야함.
    public String makePrintText(StringBuilder builder) {
        for (Textable textable : this) {
            builder.append(textable.getText());
        }
        return builder.toString();
    }

    public static class View extends AbstractHolder<PrintConsole> {
        @BindView(R2.id.console_text_view)
        TextView textView;

        public View(@NonNull ViewGroup parent) {
            super(parent, R.layout.holder_console);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(PrintConsole printConsole) {
            StringBuilder builder = new StringBuilder();
            printConsole.makePrintText(builder);
            textView.setText(builder);
        }
    }
}
