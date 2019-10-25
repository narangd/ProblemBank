package sykim.person.editor.dialog;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;

import sykim.person.editor.base.ListListener;
import sykim.person.editor.execute.Executable;

public abstract class ExecutableDialog<T extends Executable> {
    private static final String TAG = "ExecutableDialog";

    protected enum Mode {
        NEW, EDIT
    }

    protected final AlertDialog dialog;
    protected final View root;

    private Mode mode = Mode.NEW;
    private int index;

    protected ExecutableDialog(Context context, @LayoutRes final int resource, ListListener<Executable> listener) {
        dialog = new AlertDialog.Builder(context)
                .setView(root = LayoutInflater.from(context)
                        .inflate(resource, null, false))
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
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

    /**
     * 닫기를 시도하므로 Constant 값 검증을 시도함.
     */
    protected abstract boolean tryCommit();

    /**
     * Dialog 닫기가 실행되기 전에 한번 실행된다.
     */
    protected abstract T onCommit();

    /**
     * ExecutableDialog 에서 먼저 모드와 index 저장후 호출되는 함수.
     * @param t 수정시 사용.
     */
    protected abstract void onLoad(T t);

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
