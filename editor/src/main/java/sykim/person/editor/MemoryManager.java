package sykim.person.editor;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Iterator;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import sykim.person.editor.constant.Constant;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemoryManager {
    /**
     * key: variable
     * 이름으로 조회되도록 개발되어야함.(변수 이름)
     */
    HashMap<String, Constant> variableMap = new HashMap<>();
    /**
     * key: function
     */
    HashMap<String, Function> functionMap = new HashMap<>();

    public Constant getVariable(String key) {
        return variableMap.get(key);
    }

    public void set(Variable variable) {
        variableMap.put(variable.getName(), variable.getConstant());
    }

    public void clear() {
        variableMap.clear();
    }

    public void add(Variable variable) {
        variableMap.put(variable.getName(), variable.getConstant());
    }


    @NonNull
    public Iterable<String> iteratorVariable() {
        return variableMap.keySet();
    }
}
