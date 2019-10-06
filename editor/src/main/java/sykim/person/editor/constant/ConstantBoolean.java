package sykim.person.editor.constant;

import java.math.BigInteger;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;


@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConstantBoolean extends Constant {

    Boolean bool;

    public ConstantBoolean() {
        super(ConstantType.INTEGER);
    }

    public ConstantBoolean(String value) throws IllegalArgumentException {
        this();
        if (bool == null) {
            bool = false;
        }
        this.bool = Boolean.parseBoolean(value);
    }

    @Override
    public String getText() {
        return bool.toString();
    }
}
