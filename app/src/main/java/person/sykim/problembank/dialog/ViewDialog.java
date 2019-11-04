package person.sykim.problembank.dialog;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public abstract class ViewDialog {
    private static final String TAG = "ViewDialog";

    protected MaterialAlertDialogBuilder builder;

    public ViewDialog(Context context, @LayoutRes int resource) {
        builder = new MaterialAlertDialogBuilder(context);

        View root = LayoutInflater.from(context)
                .inflate(resource, null);
        bind(root);
        builder.setView(root)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null);
    }

    public AlertDialog show() {
        AlertDialog dialog = builder.create();
        // [Prevent Dialog dismiss], 다른 작업(Executable 검증)을 위한 Dialog 닫기 방지.
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                if (tryCommit()) {
                    Log.d(TAG, "show: committed");
                    commit();
                    dialog.dismiss();
                    return;
                }
                Log.d(TAG, "show: not committed");
            });
        });
        dialog.show();
        return dialog;
    }

    protected abstract void bind(View view);

    protected abstract boolean tryCommit();

    protected abstract void commit();
}
