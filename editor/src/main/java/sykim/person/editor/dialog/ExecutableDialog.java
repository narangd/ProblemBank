package sykim.person.editor.dialog;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;

import sykim.person.editor.execute.Executable;

public abstract class ExecutableDialog {
    private static final String TAG = "ExecutableDialog";

    protected final AlertDialog dialog;
    protected final View root;

    protected OnCommitEventListener listener;

    public ExecutableDialog(Context context, @LayoutRes final int resource) {
        dialog = new AlertDialog.Builder(context)
                .setView(root = LayoutInflater.from(context)
                        .inflate(resource, null, false))
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        // prevent dismiss, 다른 작업을 위한 닫기 방지
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                if (tryCommit()) {
                    Log.d(TAG, "VariableDialog: dismiss");
                    onCommit();
                    dialog.dismiss();
                }
            });
        });
    }

    /**
     * 닫기를 시도하므로 Constant 값 검증을 시도함.
     */
    protected abstract boolean tryCommit();

    /**
     * Dialog 닫기가 실행되기 전에 한번 실행된다.
     */
    protected abstract void onCommit();

    public final ExecutableDialog setListener(OnCommitEventListener listener) {
        this.listener = listener;
        return this;
    }

    public final void show() {
        dialog.show();
    }

    public interface OnCommitEventListener {
        void onCommit(Executable t);
    }
}
