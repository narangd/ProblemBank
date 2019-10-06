package sykim.person.editor.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import sykim.person.editor.Function;
import sykim.person.editor.Program;
import sykim.person.editor.R;
import sykim.person.editor.R2;
import sykim.person.editor.execute.Executable;

public class ConsoleDialog {
    AlertDialog.Builder builder;
    @BindView(R2.id.console_text_view)
    TextView consoleTextView;

    @SuppressLint("InflateParams")
    public ConsoleDialog(Context context) {
        View root = LayoutInflater.from(context)
                .inflate(R.layout.holder_console, null, false);
        ButterKnife.bind(this, root);
        builder = new AlertDialog.Builder(context)
                .setTitle("Console")
                .setView(root)
                .setPositiveButton(android.R.string.ok, null);

    }

    public ConsoleDialog setSource(Executable executable) {
        Program.getInstance().clear();
        executable.onExecute();

        consoleTextView.setText(Program.getInstance().getConsoleText());
        return this;
    }

    public AlertDialog show() {
        return builder.show();
    }
}
