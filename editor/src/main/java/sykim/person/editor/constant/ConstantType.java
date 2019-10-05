package sykim.person.editor.constant;

public enum ConstantType {
    TEXT,
    BOOLEAN,
    FLOAT,
    NUMBER,
    ;

    public Constant make(String value) throws IllegalArgumentException {
        switch (this) {
            case TEXT:
                return new ConstantText(value);
            case BOOLEAN:
            case FLOAT:
            case NUMBER:
        }
        throw new IllegalArgumentException("unknown type");
    }

    public static ConstantType parse(String typeString) {
        try {
            return ConstantType.valueOf(typeString.toUpperCase());
        } catch (Exception e) {
            return TEXT;
        }
    }
}
