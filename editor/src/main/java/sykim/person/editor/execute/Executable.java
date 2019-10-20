package sykim.person.editor.execute;

import android.content.Context;

import sykim.person.editor.base.ListListener;

public interface Executable {
    void onExecute();
    void openEditor(Context context, ListListener<Executable> listener, int index);
}
