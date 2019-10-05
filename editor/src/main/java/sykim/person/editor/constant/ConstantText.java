package sykim.person.editor.constant;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;


@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConstantText extends Constant {

    String text;

    public ConstantText() {
        super(ConstantType.TEXT);
    }

    public ConstantText(String text) throws IllegalArgumentException {
        this();
        if (text == null) {
            text = "";
        }
        this.text = text;
    }
}
