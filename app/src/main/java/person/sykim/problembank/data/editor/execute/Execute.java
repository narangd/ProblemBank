package person.sykim.problembank.data.editor.execute;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public abstract class Execute {
    ExecuteType type;
    public abstract void onExecute();
}
