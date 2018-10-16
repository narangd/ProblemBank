package person.sykim.problembank.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import person.sykim.problembank.R;

public class LoginDialog {
    private AlertDialog.Builder builder;

    @BindView(R.id.username_edittext)
    EditText usernameEditText;
    @BindView(R.id.password_edittext)
    EditText passwordEditText;

    public LoginDialog(Context context) {
        builder = new AlertDialog.Builder(context);
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context)
                .inflate(R.layout.dialog_login, null);
        ButterKnife.bind(this, root);

        builder.setTitle("Login")
                .setView(root)
                .setNegativeButton(android.R.string.cancel, null);
    }

    public LoginDialog setPositiveButton(final OnSubmitListener onSubmitListener) {
        builder.setPositiveButton("Login", (dialog, which) -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            onSubmitListener.onSubmit(username, password);
        });
        return this;
    }

    public AlertDialog show() {
        return builder.show();
    }

    public interface OnSubmitListener {
        void onSubmit(String username, String password);
    }
}
