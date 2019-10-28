package sykim.person.editor.execute;

import android.view.View;

import androidx.annotation.LayoutRes;

import lombok.Getter;

@Getter
public abstract class ExecutableMakeAdapter<T extends Executable> {

    public enum Mode {
        NEW, EDIT
    }

    Mode mode = Mode.NEW;
    int index = -1;

    final String title;
    final int resource;

    public ExecutableMakeAdapter(String title, @LayoutRes int resource) {
        this.title = title;
        this.resource = resource;
    }

    public final void setExecutable(int index, T t) {
        this.index = index;
        mode = Mode.EDIT;
        onLoad(t);
    }

    public abstract void bind(View root);

    /**
     * 닫기를 시도하므로 Constant 값 검증을 시도함.
     */
    public abstract boolean tryCommit();

    /**
     * Dialog 닫기가 실행되기 전에 한번 실행된다.
     */
    public abstract T onCommit();

    /**
     * ExecutableDialog 에서 먼저 모드와 index 저장후 호출되는 함수.
     * @param t 수정시 사용.
     */
    public abstract void onLoad(T t);

    /**
     * Dialog 로 실행할시 너무 많은 정보를 수정하게 되는 경우 FullDialog 로 이동하게 처리하는 여부
     */
    public abstract boolean requireAdvance();
}
