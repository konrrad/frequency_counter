package sentence;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Set;

@AllArgsConstructor
@EqualsAndHashCode
public class Category {
    private final Set<Character> characters;
    private final int length;


    @Override
    public String toString() {
        return String.format("{%s, %d}",characters.toString(),length);
    }
}
