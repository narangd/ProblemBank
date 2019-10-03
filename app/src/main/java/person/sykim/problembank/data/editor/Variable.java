package person.sykim.problembank.data.editor;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import person.sykim.problembank.data.editor.constant.Constant;
import person.sykim.problembank.data.editor.constant.Textable;

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
