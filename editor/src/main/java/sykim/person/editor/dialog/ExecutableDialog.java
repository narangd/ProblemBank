package sykim.person.editor.dialog;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import sykim.person.editor.R;
import sykim.person.editor.base.ListListener;
import sykim.person.editor.execute.Executable;
import sykim.person.editor.execute.ExecutableMakeAdapter;
import sykim.person.editor.fragment.ExecutableFragment;

public class ExecutableDialog <T extends Executable> {
    private static final String TAG = "ExecutableDialog";

    public static <T extends Executable> void create(Context context, ExecutableMakeAdapter<T> adapter, ListListener<Executable> listener) {
        new ExecutableDialog<>(context, adapter, listener).show();
    }

    public static <T extends Executable> void edit(
            Context context, ExecutableMakeAdapter<T> adapter, ListListener<Executable> listener,
            int index, T executable) {
        ExecutableDialog<T> dialog = new ExecutableDialog<>(context, adapter, listener);
        dialog.adapter.setExecutable(index, executable);
        dialog.show();
    }

    protected final AlertDialog dialog;

    private final ExecutableMakeAdapter<T> adapter;


    private ExecutableDialog(Context context, ExecutableMakeAdapter<T> adapter, ListListener<Executable> listener) {
        this.adapter = adapter;
        View root;
        dialog = new MaterialAlertDialogBuilder(context)
                .setView(root = LayoutInflater.from(context)
                        .inflate(adapter.getResource(), null, false))
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .setNeutralButton(R.string.advance, (dialog, which) -> {
                    if (context instanceof FragmentActivity) {
                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        transaction.addToBackStack(null);
                        ExecutableFragment.create(fragmentManager, adapter, listener);
                    } else {
                        Log.e(TAG, "ExecutableDialog: context not instanceof FragmentActivity");
                    }
                    dialog.dismiss();
                })
                .create();

        adapter.bind(root);

        // [Prevent Dialog dismiss], 다른 작업(Executable 검증)을 위한 Dialog 닫기 방지.
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                if (adapter.tryCommit()) {
                    Log.d(TAG, "VariableDialog: dismiss");

                    switch (adapter.getMode()) {
                        case NEW: listener.add(adapter.onCommit()); break;
                        case EDIT: listener.update(adapter.getIndex(), adapter.onCommit()); break;
                    }
                    dialog.dismiss();
                }
            });
            // Builder 에서 Neutral 버튼을 생성하고, 이곳에서 필요하지 않을시 보이지 않게 처리함.
            if (!adapter.requireAdvance()) {
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setVisibility(View.GONE);
            }
        });
    }

    private void show() {
        dialog.show();
    }
}
