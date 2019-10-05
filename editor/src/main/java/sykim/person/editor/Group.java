package sykim.person.editor;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

/**
 * Source > 내부 클래스
 *
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    String name;
    @Builder.Default
    List<Function> functions = new ArrayList<>();

    public Group(@NonNull String name) {
        this.name = name;
    }
}
