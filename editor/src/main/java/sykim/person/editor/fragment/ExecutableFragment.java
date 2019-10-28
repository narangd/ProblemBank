package sykim.person.editor.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ExecutableFragment<T extends Executable> extends DialogFragment {
    private static final String TAG = "ExecutableFragment";

    public static <T extends Executable> void create(FragmentManager fragmentManager, ExecutableMakeAdapter<T> adapter, ListListener<Executable> listener) {
        new ExecutableFragment<>(adapter, listener)
                .show(fragmentManager);
    }

    public static <T extends Executable> void edit(
            FragmentManager fragmentManager, ExecutableMakeAdapter<T> adapter, ListListener<Executable> listener,
            int index, T executable) {
        ExecutableFragment<T> dialog = new ExecutableFragment<>(adapter, listener);
        dialog.adapter.setExecutable(index, executable);
        dialog.show(fragmentManager);
    }

    private final ExecutableMakeAdapter<T> adapter;
    private final ListListener<Executable> listener;

    private ExecutableFragment(ExecutableMakeAdapter<T> adapter, ListListener<Executable> listener) {
        this.adapter = adapter;
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.layout_full_dialog, container, false);
        View content = inflater.inflate(adapter.getResource(), container, false);
        ViewGroup layout = root.findViewById(R.id.dialog_content_layout);
        layout.addView(content);
        adapter.bind(content);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle(adapter.getTitle());
        toolbar.inflateMenu(R.menu.make);
        toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_create) {
                if (adapter.tryCommit()) {
                    listener.add(adapter.onCommit());
                    dismiss();
                }
            }
            else if (itemId == R.id.action_update) {
                if (adapter.tryCommit()) {
                    listener.update(adapter.getIndex(), adapter.onCommit());
                    dismiss();
                }
            }
            else if (itemId == R.id.action_delete) {
                new MaterialAlertDialogBuilder(view.getContext())
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton(android.R.string.ok, ((dialog, which) -> {
                            listener.delete(adapter.getIndex());
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
        if (adapter.getMode() == ExecutableMakeAdapter.Mode.EDIT) {
            menu.findItem(R.id.action_create).setVisible(false);
            menu.findItem(R.id.action_update).setVisible(true);
            menu.findItem(R.id.action_delete).setVisible(true);
        }
    }

    private void show(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.addToBackStack(null);

//        transaction.add(android.R.id.content, this).addToBackStack(null).commit();
        this.show(transaction, "dialog");
    }
}
