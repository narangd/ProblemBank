package sykim.person.editor.constant;

public enum ConstantType {
    TEXT,
    BOOLEAN,
    DECIMAL,
    INTEGER,
    ;

    public Constant make(String value) throws IllegalArgumentException {
        try {
            switch (this) {
                case TEXT:
                    return new ConstantText(value);
                case INTEGER:
                    return new ConstantInteger(value);
                case DECIMAL:
                    return new ConstantDecimal(value);
                case BOOLEAN:
                    return new ConstantBoolean(value);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid format");
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
