package person.sykim.problembank.data.editor.constant;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Constant {
    ConstantType type;

    public Constant(ConstantType type) {
        this.type = type;
    }

    public abstract String getValue();
}
