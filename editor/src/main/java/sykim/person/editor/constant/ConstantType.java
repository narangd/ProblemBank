package sykim.person.editor.constant;

public enum ConstantType {
    TEXT,
    BOOLEAN,
    DECIMAL,
    INTEGER,
    ;

    /**
     * Constant 생성하면서 에러를 발생하여 처리하도록 수정.
     * @param value
     * @return
     * @throws IllegalArgumentException
     */
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
