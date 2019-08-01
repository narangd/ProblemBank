package person.sykim.problembank.data.editor.execute;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import person.sykim.problembank.R;
import person.sykim.problembank.data.editor.AbstractHolder;
import person.sykim.problembank.data.editor.constant.ConstantText;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PrintConsole implements ExecuteListener {

    @Builder.Default
    ConstantText text = new ConstantText("test");

    @Override
    public void onExecute() {
        // console.print(makePrintText());
        //
    }

    String makePrintText() {
        return text.getValue();
    }

    public void setText(ConstantText text) {
        this.text = text;
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
