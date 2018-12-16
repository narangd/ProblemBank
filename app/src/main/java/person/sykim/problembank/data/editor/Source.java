package person.sykim.problembank.data.editor;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Source {
    List<Group> groups = new ArrayList<>();
    Function main = new Function("main");
}
