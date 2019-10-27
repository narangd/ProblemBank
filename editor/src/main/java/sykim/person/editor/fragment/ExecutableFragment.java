package sykim.person.editor.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.layout_full_dialog, container, false);
        ViewGroup layout = root.findViewById(R.id.dialog_scroll_view);
        layout.addView(this.root = inflater.inflate(resource, container, false));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle(title);
        toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_create) {
                if (tryCommit()) {
                    listener.add(onCommit());
                    dismiss();
                }
            }
            else if (itemId == R.id.action_update) {
                if (tryCommit()) {
                    listener.update(index, onCommit());
                    dismiss();
                }
            }
            else if (itemId == R.id.action_delete) {
                new MaterialAlertDialogBuilder(view.getContext())
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton(android.R.string.ok, ((dialog, which) -> {
                            listener.delete(index);
                            dismiss();
                        }))
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
            }
            return true;
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullDialogTheme);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mode == Mode.EDIT) {
            menu.findItem(R.id.action_create).setVisible(false);
            menu.findItem(R.id.action_update).setVisible(true);
            menu.findItem(R.id.action_delete).setVisible(true);
        }
    }

    protected final Mode getMode() {
        return mode;
    }
    protected final ListListener<Executable> getListener() { return listener; }

    public final ExecutableFragment<T> setExecutable(int index, T t) {
        this.index = index;
        mode = Mode.EDIT;
        onLoad(t);
        return this;
    }

    public final void show(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.addToBackStack(null);
//        super.show(transaction, "dialog");
//        transaction.add(android.R.id.content, this).addToBackStack(null).commit();
        this.show(transaction, "dialog");
    }
}
