package person.sykim.problembank.data.editor.constant;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;


@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConstantText extends Constant {

    String text;

    public ConstantText(String text) throws IllegalArgumentException {
        super(ConstantType.TEXT);
        if (text == null) {
            text = "";
        }
        this.text = text;
    }
}
