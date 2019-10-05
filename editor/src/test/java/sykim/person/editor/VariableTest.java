package sykim.person.editor;

import org.junit.Test;

import sykim.person.editor.constant.ConstantText;

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
