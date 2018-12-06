package person.sykim.problembank.data.editor;

import lombok.Data;

@Data
public class Execute {
    String name;
    String param;

    public Execute(String name, String param) {
        this.name = name;
        this.param = param;
    }
}
