package sentence;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sentence {
    @Getter
    private final String original;
    @Getter
    private final Set<Character> desiredChars;

    public Sentence(String original, Set<Character> desiredChars) {
        this.original = original;
        this.desiredChars = desiredChars.stream().map(Character::toLowerCase).collect(Collectors.toSet());
    }

    public Map<Category, List<Word>> createCategoryWordMap() {
        HashMap<Category, List<Word>> map = new HashMap<>();
        splitSentenceBySpecialCharacters()
                .map(String::toLowerCase)
                .filter(this::containsDesired)
                .map(originalWord -> originalWord.toLowerCase(Locale.ROOT))
                .map(originalWord -> new Word(originalWord, desiredChars))
                .forEach(word -> putToProperCategory(word, map));
        return map;
    }


    private void putToProperCategory(Word word, HashMap<Category, List<Word>> map) {
        if (map.containsKey(word.getCategory())) {
            var wordsForCategory = map.remove(word.getCategory());
            List<Word> newList = new ArrayList<>(wordsForCategory);
            newList.add(word);
            map.put(word.getCategory(), newList);
        } else {
            map.put(word.getCategory(), Collections.singletonList(word));
        }
    }


    private boolean containsDesired(String s) {
        return s.chars().mapToObj(c -> (char) c).anyMatch(desiredChars::contains);
    }

    public Stream<String> splitSentenceBySpecialCharacters() {
        return Arrays.stream(this.original.split("[\\Q( !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~);\\E]")).filter(s -> !s.equals(""));
    }

    public int getSplitSequenceLength()
    {
        return splitSentenceBySpecialCharacters().map(String::length).reduce(0,Integer::sum);
    }
}
