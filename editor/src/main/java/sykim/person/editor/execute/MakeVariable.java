package sykim.person.editor.execute;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import butterknife.ButterKnife;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import sykim.person.editor.AbstractHolder;
import sykim.person.editor.Program;
import sykim.person.editor.R;
import sykim.person.editor.Variable;
import sykim.person.editor.constant.Constant;
import sykim.person.editor.constant.ConstantType;

@Data
@EqualsAndHashCode(callSuper = true)
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MakeVariable extends Execute {

    Variable variable;

    public MakeVariable(ConstantType type, String name, String value) {
        super(ExecuteType.MAKE_VARIABLE);
        Constant constant = type.make(value);
        variable = new Variable(name, constant);
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
        Program.getInstance().memory.add(variable);
    }

    public static class View extends AbstractHolder<MakeVariable> {
//        @BindView(R.id.variable_name_text_view)
        TextView nameTextView;
//        @BindView(R.id.variable_value_text_view)
        TextView valueTextView;

        public View(@NonNull ViewGroup parent) {
            super(parent, R.layout.holder_variable);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void bind(MakeVariable makeVariable) {
            Variable variable = makeVariable.variable;
            nameTextView.setText(variable.getName());
            valueTextView.setText(variable.getText());
        }
    }
}
