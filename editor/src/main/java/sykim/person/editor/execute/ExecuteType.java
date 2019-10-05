package sykim.person.editor.execute;

public enum ExecuteType {
    MAKE_VARIABLE,
    PRINT_CONSOLE,
    FUNCTION;

    public static ExecuteType parse(String typeString) {
        try {
            return ExecuteType.valueOf(typeString.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
