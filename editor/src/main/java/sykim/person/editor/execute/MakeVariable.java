package sykim.person.editor.execute;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import sykim.person.editor.AbstractHolder;
import sykim.person.editor.NameSpaceManager;
import sykim.person.editor.Program;
import sykim.person.editor.R;
import sykim.person.editor.R2;
import sykim.person.editor.Variable;
import sykim.person.editor.base.ListListener;
import sykim.person.editor.constant.Constant;
import sykim.person.editor.constant.ConstantType;
import sykim.person.editor.dialog.ExecutableDialog;

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
        ExecutableDialog.edit(context, new MakeVariable.Adapter(), listener, index, this);
    }

    public static class Holder extends AbstractHolder<MakeVariable> {
        @BindView(R2.id.variable_name_text_view)
        TextView nameTextView;
        @BindView(R2.id.variable_value_text_view)
        TextView valueTextView;

        public Holder(@NonNull ViewGroup parent) {
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

    public static class Adapter extends ExecutableMakeAdapter<MakeVariable> {
        private static final String TAG = "MakeVariableFragment";

        @BindView(R2.id.variable_type_text_button)
        MaterialButton typeTextButton;
        @BindView(R2.id.variable_type_integer_button)
        MaterialButton typeIntegerButton;
        @BindView(R2.id.variable_type_decimal_button)
        MaterialButton typeDecimalButton;
        @BindView(R2.id.variable_type_boolean_button)
        MaterialButton typeBooleanButton;

        @BindView(R2.id.variable_name_edit_text)
        TextInputEditText nameEditText;
        @BindView(R2.id.variable_value_edit_text)
        TextInputEditText valueEditText;
        @BindView(R2.id.variable_name_layout)
        TextInputLayout nameLayout;
        @BindView(R2.id.variable_value_layout)
        TextInputLayout valueLayout;

        private ConstantType type = ConstantType.TEXT;
        private MaterialButton prev;
        private String prevName;

        public Adapter() {
            super("Variable", R.layout.dialog_variable);
        }

        @Override
        public void bind(View root) {
            ButterKnife.bind(this, root);
        }

        private String getValueFromEditText() {
            Editable editable = valueEditText.getText();
            return editable != null ? editable.toString() : "";
        }
        private String getNameFromEditText() {
            Editable editable = nameEditText.getText();
            return editable != null ? editable.toString() : "";
        }

        /**
         * Variable Type 변경시 value 검증을 다시해야함.
         * @param button
         */
        @OnClick({
                R2.id.variable_type_text_button,
                R2.id.variable_type_integer_button,
                R2.id.variable_type_decimal_button,
                R2.id.variable_type_boolean_button
        })
        void onConstantTypeClick(MaterialButton button) {
            if (prev != null) {
                prev.setChecked(false);
            }
            prev = button;
            button.setChecked(true);

            validateValue(ConstantType.parse(button.getTag()+""));
        }

        /**
         * 이름 검증.
         * @return
         */
        private String validateName() {
            final String name = getNameFromEditText();
            nameLayout.setError(null);
            if (!name.matches("[a-zA-Z_][a-zA-Z_0-9]*")) {
                nameLayout.setError("형식에 맞지 않습니다.");
                return null;
            }
            switch (getMode()) {
                case NEW:
                    if (NameSpaceManager.getInstance().contains(name)) {
                        nameLayout.setError("이미 같은 이름이 존재합니다.");
                        return null;
                    }
                    break;
                case EDIT:
                    if (!name.equals(prevName) && NameSpaceManager.getInstance().contains(name)) {
                        nameLayout.setError("이미 같은 이름이 존재합니다.");
                        return null;
                    }
                    break;
            }
            if (getMode() == Mode.NEW && NameSpaceManager.getInstance().contains(name)) {
                nameLayout.setError("이미 같은 이름이 존재합니다.");
                return null;
            }
            return name;
        }

        /**
         * ConstantType을 변경하게 될시 처리하게되는 기능.
         * @param type
         * @return
         */
        private Constant validateValue(ConstantType type) {
            final String value = getValueFromEditText();
            valueLayout.setError(null);
            this.type = type;
            try {
                // Constant 생성에 Exception 발생하는지 확인.
                return type.make(value);

            } catch (IllegalArgumentException e) {
                valueLayout.setError(e.getMessage());
                valueLayout.requestFocus();
                valueEditText.requestFocus();
                return null;
            }
        }

        @OnTextChanged(R2.id.variable_name_edit_text)
        void onChangeName() {
            validateName();
        }

        @OnTextChanged(R2.id.variable_value_edit_text)
        void onChangeValue() {
            validateValue(type);
        }

        /**
         * 닫기를 시도하므로 Constant 값 검증을 시도함.
         */
        @Override
        public boolean tryCommit() {
            return validateName() != null &&
                    validateValue(type) != null;
        }

        /**
         * Dialog 닫기가 실행되기 전에 한번 실행된다.
         */
        @Override
        public MakeVariable onCommit() {
            String name = validateName();
            MakeVariable makeVariable = new MakeVariable(name, validateValue(type));

            // NameSpace 에 이름 등록.
            switch (getMode()) {
                case NEW: NameSpaceManager.getInstance().add(name); break;
                case EDIT: NameSpaceManager.getInstance().change(prevName, name); break;
            }

            return makeVariable;
        }

        /**
         * 이미 입력된 Variable을 수정하거나 삭제할 때 사용하는 기능.
         * @param makeVariable
         * @return
         */
        @Override
        public void onLoad(MakeVariable makeVariable) {
            Variable variable = makeVariable.getVariable();

            nameEditText.setText(prevName = variable.getName());
            valueEditText.setText(variable.getConstant().getText());

            switch (type = variable.getConstant().getType()) {
                case TEXT: onConstantTypeClick(typeTextButton); break;
                case INTEGER: onConstantTypeClick(typeIntegerButton); break;
                case DECIMAL: onConstantTypeClick(typeDecimalButton); break;
                case BOOLEAN: onConstantTypeClick(typeBooleanButton); break;
            }
        }

        @Override
        public boolean requireAdvance() {
            return true;
        }
    }
}
