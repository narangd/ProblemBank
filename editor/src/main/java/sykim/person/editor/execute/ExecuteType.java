package sykim.person.editor.execute;

public enum ExecuteType {
    MAKE_VARIABLE,
    PRINT_CONSOLE,
//    FLOAT,
//    NUMBER,
    ;

//    public Constant make(String value) throws IllegalArgumentException {
//        switch (this) {
//            case MAKE_VARIABLE:
//                return new ConstantText(value);
//            case BOOLEAN:
//            case PRINT_CONSOLE:
//            case NUMBER:
//        }
//        throw new IllegalArgumentException("unknown type");
//    }

    public static ExecuteType parse(String typeString) {
        try {
            return ExecuteType.valueOf(typeString.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
