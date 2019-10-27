package sykim.person.editor.dialog;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import sykim.person.editor.R;
import sykim.person.editor.base.ListListener;
import sykim.person.editor.execute.Executable;
import sykim.person.editor.execute.ExecutableMakeAdapter;

public abstract class ExecutableDialog<T extends Executable> implements ExecutableMakeAdapter<T> {
    private static final String TAG = "ExecutableDialog";

    protected enum Mode {
        NEW, EDIT
    }

    protected final AlertDialog dialog;
    protected final View root;

    private Mode mode = Mode.NEW;
    private int index;

    protected ExecutableDialog(Context context, @LayoutRes final int resource, ListListener<Executable> listener) {
        dialog = new MaterialAlertDialogBuilder(context, R.style.ExecutableDialogTheme)
                .setView(root = LayoutInflater.from(context)
                        .inflate(resource, null, false))
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .setNeutralButton(R.string.delete, (dialog, which) -> {
                    new MaterialAlertDialogBuilder(context)
                            .setTitle("Confirm")
                            .setMessage("삭제하시겠습니까?")
                            .setPositiveButton(android.R.string.ok, ((dialog1, which1) -> {
                                listener.delete(index);
                            }))
                            .setNegativeButton(android.R.string.cancel, null)
                            .show();
                })
                .create();

        // [Prevent Dialog dismiss], 다른 작업(Executable 검증)을 위한 Dialog 닫기 방지.
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                if (tryCommit()) {
                    Log.d(TAG, "VariableDialog: dismiss");

                    switch (mode) {
                        case NEW: listener.add(onCommit()); break;
                        case EDIT: listener.update(index, onCommit()); break;
                    }
                    dialog.dismiss();
                }
            });
        });
    }

    protected Mode getMode() {
        return mode;
    }

    public final ExecutableDialog setExecutable(int index, T t) {
        this.index = index;
        mode = Mode.EDIT;
        onLoad(t);
        return this;
    }

    public final void show() {
        dialog.show();
    }
}
