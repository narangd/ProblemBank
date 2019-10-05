package sykim.person.editor;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import sykim.person.editor.constant.Constant;
import sykim.person.editor.constant.Textable;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Variable implements Textable {
    @EqualsAndHashCode.Exclude
    String name;
    Constant constant;

    public Variable(String name, Constant constant) {
        this.name = name;
        this.constant = constant;
    }

    @Override
    public String getText() {
        return constant.getText();
    }

}
