package sykim.person.editor.execute;

public interface ExecutableMakeAdapter<T extends Executable> {

    enum Mode {
        NEW, EDIT
    }

    /**
     * 닫기를 시도하므로 Constant 값 검증을 시도함.
     */
    boolean tryCommit();

    /**
     * Dialog 닫기가 실행되기 전에 한번 실행된다.
     */
    T onCommit();

    /**
     * ExecutableDialog 에서 먼저 모드와 index 저장후 호출되는 함수.
     * @param t 수정시 사용.
     */
    void onLoad(T t);

    /**
     * Dialog 로 실행할시 너무 많은 정보를 수정하게 되는 경우 FullDialog 로 이동하게 처리하는 여부
     */
    boolean requireAdvance();
}
