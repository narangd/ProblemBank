package sykim.person.editor;

import org.junit.Test;

import sykim.person.editor.constant.Constant;
import sykim.person.editor.constant.ConstantBoolean;
import sykim.person.editor.constant.ConstantDecimal;
import sykim.person.editor.constant.ConstantInteger;
import sykim.person.editor.constant.ConstantText;

public class ConstantTest {
    @Test
    public void parseTest() {

        print(new ConstantText("abcd text test !@34"));
        print(new ConstantInteger("123"));
        print(new ConstantDecimal("123.456"));
        print(new ConstantBoolean("true"));

        // parse error
        String input;

        input = "abc123";
        try {
            new ConstantInteger(input);
        } catch (Exception e) {
            System.out.println("integer parse error : " +input);
        }

        input = "abc123.45";
        try {
            new ConstantDecimal(input);
        } catch (Exception e) {
            System.out.println("decimal parse error : " +input);
        }

    }

    private void print(Constant constant) {
        System.out.println(constant.getType() + " : "+ constant.getText());
    }
}
