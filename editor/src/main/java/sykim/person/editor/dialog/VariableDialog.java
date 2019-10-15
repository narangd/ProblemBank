package sykim.person.editor.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import sykim.person.editor.R;
import sykim.person.editor.R2;
import sykim.person.editor.Variable;
import sykim.person.editor.constant.Constant;
import sykim.person.editor.constant.ConstantType;

public class VariableDialog {
    private static final String TAG = "VariableDialog";
    AlertDialog dialog;
    ConstantType type = ConstantType.TEXT;

    @BindView(R2.id.variable_type_text_button)
    Button typeTextButton;
    @BindView(R2.id.variable_type_integer_button)
    Button typeIntegerButton;
    @BindView(R2.id.variable_type_decimal_button)
    Button typeDecimalButton;
    @BindView(R2.id.variable_type_boolean_button)
    Button typeBooleanButton;

    @BindView(R2.id.variable_name_edit_text)
    EditText nameEditText;
    @BindView(R2.id.variable_value_edit_text)
    EditText valueEditText;

    Button prev;

    @SuppressLint("InflateParams")
    public VariableDialog(Context context) {
        View root = LayoutInflater.from(context)
                .inflate(R.layout.dialog_variable, null, false);
        ButterKnife.bind(this, root);
        dialog = new AlertDialog.Builder(context)
                .setTitle("Console")
                .setView(root)
                .setPositiveButton(android.R.string.ok, null)
                .create();

        // prevent dismiss, 다른 작업을 위한 닫기 방지
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                if (onDismiss()) {
                    // Dismiss once everything is OK.
                    dialog.dismiss();
                }
            });
        });

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
    void onClick(Button button) {
        if (prev != null) {
            prev.setAlpha(0.5f);
        }
        prev = button;
        button.setAlpha(1f);

        validateValue(ConstantType.parse(button.getTag()+""));
    }

    /**
     * ConstantType을 변경하게 될시 처리하게되는 기능.
     */
    private boolean validateValue(ConstantType type) {
        String value = valueEditText.getText().toString();
        try {
            type.make(value);

        } catch (IllegalArgumentException e) {
            valueEditText.setError("error:"+e.getMessage()+", value:"+value);
            return false;
        }
        return false;
    }

    @OnTextChanged(R2.id.variable_value_edit_text)
    void onChangeValue() {
        validateValue(type);
    }

    /**
     * Variable 추가,수정 완료
     */
    private boolean onDismiss() {
        return validateValue(type);
    }

    /**
     * 이미 입력된 Variable을 수정하거나 삭제할 때 사용하는 기능.
     * @param variable
     * @return
     */
    public VariableDialog setVariable(Variable variable) {
        switch (type = variable.getConstant().getType()) {
            case TEXT: onClick(typeTextButton); break;
            case INTEGER: onClick(typeIntegerButton); break;
            case DECIMAL: onClick(typeDecimalButton); break;
            case BOOLEAN: onClick(typeBooleanButton); break;
        }
        
        nameEditText.setText(variable.getName());
        valueEditText.setText(variable.getConstant().getText());

        return this;
    }

    public void show() {
        dialog.show();
    }
}
