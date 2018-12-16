package person.sykim.problembank.data.editor;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Function {
    private String name;
    // parameter
    private String returnType = "void";
    private List<Variable> variables = new ArrayList<>();
    private List<Execute> executes = new ArrayList<>();

    public Function(String name) {
        this.name = name;
    }
}
