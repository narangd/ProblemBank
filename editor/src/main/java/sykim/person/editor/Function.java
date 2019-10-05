package sykim.person.editor;

import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import sykim.person.editor.execute.Executable;

@Data
@EqualsAndHashCode(callSuper = false)
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Function {
    String name;
//    @Builder.Default
    ArrayList<Executable> list = new ArrayList<>();
    // parameter
//    String returnType = "void";
//    @Builder.Default
//    List<Variable> variables = new ArrayList<>();

//    public Function(String name) {
//        this.name = name;
//    }

//    @Override
    public void onExecute() {
    }

    public void add(Executable execute) {
        list.add(execute);
    }
}
