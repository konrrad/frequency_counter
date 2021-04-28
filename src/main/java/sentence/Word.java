package sentence;

import lombok.Data;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class Word {
    private final String originalWord;
    private final Set<Character> desiredChars;
    private final Category category;
    private Map<Character, Integer> desiredCharsFrequency;

    public Word(String originalWord, Set<Character> desiredChars) {
        this.originalWord = originalWord;
        this.desiredChars = desiredChars;
        this.desiredCharsFrequency = createDesiredCharsFrequency();
        this.category = createCategory();
    }

    private Category createCategory() {
        return new Category(desiredCharsFrequency.keySet(), originalWord.length());
    }

    private Map<Character, Integer> createDesiredCharsFrequency() {
        return this.desiredCharsFrequency = originalWord.chars()
                .mapToObj(c -> (char) c)
                .filter(desiredChars::contains)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(c -> 1)));
    }

    public int getNumOfDesiredChars() {
        return this.desiredCharsFrequency.values().stream().reduce(0, Integer::sum);
    }
}
