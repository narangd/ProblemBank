package person.sykim.problembank.data.editor;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Source > 내부 클래스
 */
@Data
public class Group {
    private String name;
    private List<Function> functions = new ArrayList<>();

    public Group(String name) {
        if (name == null) throw new RuntimeException("name is null");
        this.name = name;
    }
}
