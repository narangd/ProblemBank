package sykim.person.editor.constant;

import java.math.BigInteger;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;


@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConstantInteger extends Constant {

    BigInteger integer;

    public ConstantInteger() {
        super(ConstantType.INTEGER);
    }

    public ConstantInteger(String value) throws IllegalArgumentException {
        this();
        if (integer == null) {
            integer = BigInteger.ZERO;
        }
        this.integer = new BigInteger(value);
    }

    @Override
    public String getText() {
        return integer.toString();
    }
}
