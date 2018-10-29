package person.sykim.problembank.data;

import lombok.Data;
import person.sykim.problembank.util.SecurityUtils;

@Data
public class User {
    String key = SecurityUtils.generateKey();
    String name;
}
