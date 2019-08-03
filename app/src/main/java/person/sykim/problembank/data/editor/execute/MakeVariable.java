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
import person.sykim.problembank.data.editor.constant.ConstantType;

@Data
@EqualsAndHashCode(callSuper = true)
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MakeVariable extends Execute {

    String name;
    Constant constant;

    public MakeVariable(ConstantType type, String name, String value) {
        super(ExecuteType.MAKE_VARIABLE);
        constant = type.make(value);
//        this.name = nameValidation(name);
        this.name = name;
    }

    private String nameValidation(String name) {
        if (!name.matches("[a-zA-Z_][a-zA-Z_0-9]*")) {
            throw new IllegalArgumentException("name이 다르게 입력되었습니다");
        }
        return null;
    }

    @Override
    public void onExecute() {
        // add variable to memory
    }

    public static class View extends AbstractHolder<MakeVariable> {
        @BindView(R.id.name_text_view)
        TextView nameTextView;
        @BindView(R.id.value_text_view)
        TextView valueTextView;

        public View(@NonNull ViewGroup parent) {
            super(parent, R.layout.holder_editor_variable);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void bind(MakeVariable makeVariable) {
            nameTextView.setText(makeVariable.name);
            valueTextView.setText(makeVariable.constant.getValue());
        }
    }
}
