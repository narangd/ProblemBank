package person.sykim.problembank.data;

import java.util.UUID;

import lombok.Data;

@Data
public class User {
    private String key = UUID.randomUUID().toString();
}
