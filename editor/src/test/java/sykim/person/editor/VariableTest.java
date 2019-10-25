package sykim.person.editor;

import org.junit.Test;

import sykim.person.editor.constant.ConstantText;
import sykim.person.editor.constant.ConstantType;
import sykim.person.editor.execute.MakeVariable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VariableTest {
    @Test
    public void equalsTest() {
        // 이름이 다르지만 값아 같은지 테스트
        Variable variableA = new Variable("first", new ConstantText("value"));
        Variable variableB = new Variable("second", new ConstantText("value"));

        assertEquals(variableA, variableB);

        // 숫자, 실수, 불린
    }

    @Test
    public void nameConflictTest() {
        Function function = new Function("main");
        MakeVariable makeVariable = new MakeVariable("A", ConstantType.TEXT.make("a value"));
        function.add(makeVariable);
        // Function 이든, Program 이든 이름 만을 처리하기에는 무리가 있음
        // function: execute 가 이름을 가진 객체인지 보장이 안됨.
        // program: 실행되더라도, if, for 같이 사라지는 경우에 대해서 대응이 안됨.
        // :: executable 에서 이름만 전용으로 가져오도록 해야되며, 생성,수정시 이를 검증해야됨.

        NameSpaceManager manager = NameSpaceManager.getInstance();
        manager.load(function);

        // A가 이미 정의되어 'true'가 나와야함.
        assertTrue(manager.contains(makeVariable.getVariable().getName()));
        // 새로운 이름을 정의하여 'false'가 나와야함.
        makeVariable.getVariable().setName("B");
        assertFalse(manager.contains(makeVariable.getVariable().getName()));
    }
}
