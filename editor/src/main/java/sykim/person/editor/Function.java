package sykim.person.editor;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import sykim.person.editor.execute.Executable;
import sykim.person.editor.execute.Execute;
import sykim.person.editor.execute.ExecuteList;
import sykim.person.editor.execute.ExecuteType;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Function extends ExecuteList<Executable> {
    String name;
    // parameter
//    String returnType = "void";

    public Function(String name) {
        super(ExecuteType.FUNCTION);
        this.name = name;
    }

    @Override
    public void onExecute() {
        for (Executable execute : this) {
            execute.onExecute();
        }
    }
}
