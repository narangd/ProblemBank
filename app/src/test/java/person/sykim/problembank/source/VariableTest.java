package person.sykim.problembank.source;

import org.junit.Test;

import person.sykim.problembank.data.editor.Variable;
import person.sykim.problembank.data.editor.constant.ConstantText;

import static org.junit.Assert.assertEquals;

public class VariableTest {
    @Test
    public void equalsTest() {
        // 이름이 다르지만 값아 같은지 테스트
        Variable variableA = new Variable("first", new ConstantText("value"));
        Variable variableB = new Variable("second", new ConstantText("value"));

        assertEquals(variableA, variableB);

        // 숫자, 실수, 불린
    }
}
