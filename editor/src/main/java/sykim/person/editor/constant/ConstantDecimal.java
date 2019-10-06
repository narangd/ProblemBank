package sykim.person.editor.constant;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;


@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConstantDecimal extends Constant {

    BigDecimal decimal;

    public ConstantDecimal() {
        super(ConstantType.DECIMAL);
    }

    public ConstantDecimal(String value) throws IllegalArgumentException {
        this();
        if (decimal == null) {
            decimal = BigDecimal.ZERO;
        }
        this.decimal = new BigDecimal(value);
    }

    @Override
    public String getText() {
        return decimal.toString();
    }
}
