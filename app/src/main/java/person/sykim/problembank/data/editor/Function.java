package person.sykim.problembank.data.editor;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Function implements ExecuteListener {
    String name;
    // parameter
//    String returnType = "void";
    @Builder.Default
    List<Variable> variables = new ArrayList<>();
    @Builder.Default
    List<ExecuteListener> executes = new ArrayList<>();

//    public Function(String name) {
//        this.name = name;
//    }

    @Override
    public void onExecute() {

    }
}
