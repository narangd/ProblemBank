package sykim.person.editor;

import java.util.HashMap;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemoryManager {
    /**
     * key: variable
     * 이름으로 조회되도록 개발되어야함.(변수 이름)
     */
    HashMap<String, Variable> variableMap = new HashMap<>();
    /**
     * key: function
     */
    HashMap<String, Function> functionMap = new HashMap<>();

    public Variable getVariable(String key) {
        return variableMap.get(key);
    }

    public void set(Variable variable) {
//        variableMap.put(variable.)
    }

    public void clear() {
        variableMap.clear();
    }

    public void add(Variable variable) {
        variableMap.put(variable.getName(), variable);
    }
}
