package sykim.person.editor.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import sykim.person.editor.R;
import sykim.person.editor.R2;
import sykim.person.editor.Variable;
import sykim.person.editor.constant.Constant;
import sykim.person.editor.constant.ConstantType;
import sykim.person.editor.execute.MakeVariable;

public class VariableDialog extends ExecutableDialog {
    private static final String TAG = "VariableDialog";
    ConstantType type = ConstantType.TEXT;

    @BindView(R2.id.variable_type_text_button)
    MaterialButton typeTextButton;
    @BindView(R2.id.variable_type_integer_button)
    MaterialButton typeIntegerButton;
    @BindView(R2.id.variable_type_decimal_button)
    MaterialButton typeDecimalButton;
    @BindView(R2.id.variable_type_boolean_button)
    MaterialButton typeBooleanButton;

    MaterialButton prev;

    @BindView(R2.id.variable_name_edit_text)
    TextInputEditText nameEditText;
    @BindView(R2.id.variable_value_edit_text)
    TextInputEditText valueEditText;
    @BindView(R2.id.variable_value_layout)
    TextInputLayout valueLayout;


    @SuppressLint("InflateParams")
    public VariableDialog(Context context) {
        super(context, R.layout.dialog_variable);
        ButterKnife.bind(this, root);

        dialog.setTitle("Variable");

        // default button
        prev = typeTextButton;
    }

    /**
     * Variable 추가,수정할 때 해당 버튼클릭 처리
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
     * ConstantType을 변경하게 될시 처리하게되는 기능.
     */
    private Constant validateValue(ConstantType type) {
        Editable editable = valueEditText.getText();
        String value = editable != null ? editable.toString() : "";
        valueLayout.setError(null);
        this.type = type;
        try {
            // 변수생성에 Exception 발생하는지 확인.
            return type.make(value);

        } catch (IllegalArgumentException e) {
            valueLayout.setError(e.getMessage() + " :"+value);
            valueLayout.requestFocus();
            valueEditText.requestFocus();
            return null;
        }
    }

    /**
     * Variable Type 변경시 value 검증을 다시해야함.
     */
    @OnTextChanged(R2.id.variable_value_edit_text)
    void onChangeValue() {
        validateValue(type);
    }

    /**
     * 닫기를 시도하므로 Constant 값 검증을 시도함.
     */
    @Override
    protected boolean tryCommit() {
        return validateValue(type) != null;
    }

    /**
     * Dialog 닫기가 실행되기 전에 한번 실행된다.
     */
    @Override
    protected void onCommit() {
        Editable editable = nameEditText.getText();
        String name = editable != null ? editable.toString() : "";
        Constant constant = validateValue(type);
        listener.onCommit( new MakeVariable(name, constant) );
    }

    /**
     * 이미 입력된 Variable을 수정하거나 삭제할 때 사용하는 기능.
     * @param makeVariable
     * @return
     */
    public VariableDialog setVariable(MakeVariable makeVariable) {
        Variable variable = makeVariable.getVariable();
        switch (type = variable.getConstant().getType()) {
            case TEXT: onConstantTypeClick(typeTextButton); break;
            case INTEGER: onConstantTypeClick(typeIntegerButton); break;
            case DECIMAL: onConstantTypeClick(typeDecimalButton); break;
            case BOOLEAN: onConstantTypeClick(typeBooleanButton); break;
        }
        
        nameEditText.setText(variable.getName());
        valueEditText.setText(variable.getConstant().getText());

        return this;
    }
}
