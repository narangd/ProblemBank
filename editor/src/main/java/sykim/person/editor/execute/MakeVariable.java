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
import sykim.person.editor.Variable;
import sykim.person.editor.base.ListListener;
import sykim.person.editor.constant.Constant;
import sykim.person.editor.constant.ConstantType;
import sykim.person.editor.dialog.VariableDialog;

@Data
@EqualsAndHashCode(callSuper = true)
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MakeVariable extends Execute {

    Variable variable;

    // for json

    public MakeVariable(ConstantType type, String name, String value) {
        super(ExecuteType.MAKE_VARIABLE);
        Constant constant = type.make(value);
        variable = new Variable(name, constant);
    }
    public MakeVariable(String name, Constant constant) {
        super(ExecuteType.MAKE_VARIABLE);
        variable = new Variable(name, constant);
    }

    @Override
    public void onExecute() {
        // add variable to memory
        Program.getInstance().memory.add(variable);
    }

    @Override
    public void openEditor(Context context, ListListener<Executable> listener, int index) {
        new VariableDialog(context, listener)
                .setExecutable(index, this)
                .show();
    }

    public static class View extends AbstractHolder<MakeVariable> {
        @BindView(R2.id.variable_name_text_view)
        TextView nameTextView;
        @BindView(R2.id.variable_value_text_view)
        TextView valueTextView;

        public View(@NonNull ViewGroup parent) {
            super(parent, R.layout.holder_variable);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(MakeVariable makeVariable) {
            Variable variable = makeVariable.variable;
            nameTextView.setText(variable.getName());
            // Program.getInstance().getVariable(name)을 써야되나,
            // 각 holder 에는 프로그램을 작동시키지 않으므로 사용하지 않음.
            valueTextView.setText(variable.getConstant().getText());
        }
    }
}
