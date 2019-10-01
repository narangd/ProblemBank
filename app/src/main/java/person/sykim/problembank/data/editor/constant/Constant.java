package person.sykim.problembank.data.editor.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public abstract class Constant implements Textable {
    ConstantType type;
}
