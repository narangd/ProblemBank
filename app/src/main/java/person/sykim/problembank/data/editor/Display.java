package person.sykim.problembank.data.editor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
//@NoArgsConstructor
//@AllArgsConstructor
public class Display implements ExecuteListener {

    @Override
    public void onExecute() {

    }
}
