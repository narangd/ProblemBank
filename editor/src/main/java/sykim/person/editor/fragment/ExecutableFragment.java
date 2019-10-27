package sykim.person.editor.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import sykim.person.editor.R;
import sykim.person.editor.base.ListListener;
import sykim.person.editor.execute.Executable;
import sykim.person.editor.execute.ExecutableMakeAdapter;

public abstract class ExecutableFragment<T extends Executable> extends DialogFragment implements ExecutableMakeAdapter<T> {
    private static final String TAG = "ExecutableFragment";

//    MaterialAlertDialogBuilder builder;

    private int resource;
    private ListListener<Executable> listener;
    protected View root;

    private final String title;
    private Mode mode = Mode.NEW;
    private int index;

    protected ExecutableFragment(String title, @LayoutRes final int resource, ListListener<Executable> listener) {
        this.title = title;
        this.resource = resource;
        this.listener = listener;
//        builder = new MaterialAlertDialogBuilder(context)
//                .setView(root = LayoutInflater.from(context)
//                        .inflate(resource, null, false))
//                .setPositiveButton(android.R.string.ok, null)
//                .setNegativeButton(android.R.string.cancel, null);
//        new MaterialAlertDialogBuilder(context)
//                .setTitle("Confirm")
//                .setMessage("삭제하시겠습니까?")
//                .setPositiveButton(android.R.string.ok, ((dialog1, which1) -> {
//                    listener.delete(index);
//                }))
//                .setNegativeButton(android.R.string.cancel, null)
//                .show();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog: ");

        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;

//        if (builder == null) {
//            return dialog;
//        }

//        AlertDialog dialog = builder.create();
//
//        // [Prevent Dialog dismiss], 다른 작업(Executable 검증)을 위한 Dialog 닫기 방지.
//        dialog.setOnShowListener(dialogInterface -> {
//            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//            button.setOnClickListener(view -> {
//                if (tryCommit()) {
//                    Log.d(TAG, "VariableDialog: dismiss");
//
//                    switch (mode) {
//                        case NEW: listener.add(onCommit()); break;
//                        case EDIT: listener.update(index, onCommit()); break;
//                    }
//                    dialog.dismiss();
//                }
//            });
//        });
//        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.layout_full_dialog, container, false);
//        builder.setView(root);
        ViewGroup layout = root.findViewById(R.id.dialog_scroll_view);
        layout.addView(this.root = inflater.inflate(resource, container, false));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(this::onClose);
        toolbar.setTitle(title);
//        toolbar.inflateMenu(R.menu.make);
        toolbar.setOnMenuItemClickListener(item -> {
            dismiss();
            return true;
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    void onClose(View view) {
        Log.d(TAG, "onClose: ");
        dismiss();
    }

    protected Mode getMode() {
        return mode;
    }
    protected ListListener<Executable> getListener() { return listener; }

    public void onAdvance() {

    }

    public final ExecutableFragment<T> setExecutable(int index, T t) {
        this.index = index;
        mode = Mode.EDIT;
        onLoad(t);
        return this;
    }

    public ExecutableFragment<T> setAdvance() {
//        builder.setNeutralButton(R.string.advance, (dialog, which) -> onAdvance());
//        android.R.style.Theme_Black_NoTitleBar_Fullscreen
        return this;
    }

    public final void show(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
//        super.show(transaction, "dialog");
        transaction.add(android.R.id.content, this).addToBackStack(null).commit();
    }
}
