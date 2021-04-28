import org.junit.jupiter.api.Test;
import sentence.Category;
import sentence.Sentence;
import sentence.Word;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class SentenceTest {
    @Test
    public void splitSentenceReturnAlphabetWithoutSpecialCharacters() {
        //given
        String s = "a(b !c\"d#e$f%g&h'i(j)k*l+m,n-o.p/q:r;s<t=u>w?x@y[z]a^b_c`d{e|f}g~h)i;j";

        //when
        var sentence = new Sentence(s, mock(Set.class));
        var expected = "abcdefghijklmnopqrstuwxyzabcdefghij";

        //then
        assertEquals(expected, sentence.splitSentenceBySpecialCharacters().reduce("", String::concat));
    }

    @Test
    public void splitSentenceShouldReturnSplitted() {
        //given
        String s = "I love to work in global logic!";

        //when
        var sentence = new Sentence(s, mock(Set.class));
        var expected = Arrays.asList("I", "love", "to", "work", "in", "global", "logic");

        //then
        assertEquals(expected, sentence.splitSentenceBySpecialCharacters().collect(Collectors.toList()));
    }

    @Test
    public void splitSentenceShouldReturnEmptyFromEmpty() {
        //given
        String s = "( !\"#$%&'()*+,-./:;<=>?@[]^_`{|}~);";

        //when
        var sentence = new Sentence(s, mock(Set.class));

        //then
        assertEquals(Collections.emptyList(), sentence.splitSentenceBySpecialCharacters().collect(Collectors.toList()));
    }

    @Test
    public void createCategoryWordMapShouldReturnMap() {
        //given
        String s = "I love to work in global logic!";
        Set<Character> desiredChars = new HashSet<>(Arrays.asList('L', 'O', 'G', 'I', 'C'));

        //when
        var sentence = new Sentence(s, desiredChars);

        //then
        assertEquals(createCategoryWordMapFor1(), sentence.createCategoryWordMap());
    }

    private Map<Category, List<Word>> createCategoryWordMapFor1() {
        HashMap<Category, List<Word>> res = new HashMap<>();
        var categories = createCategoriesFor1();
        var words = createWordsFor1();
        for (int i = 0; i < categories.size(); i++) {
            res.put(categories.get(i), words.get(i));
        }
        return res;
    }

    private List<Category> createCategoriesFor1() {
        Category c1 = new Category(new HashSet<>(Arrays.asList('l', 'o')), 4);
        Category c2 = new Category(new HashSet<>(Collections.singletonList('o')), 4);
        Category c3 = new Category(new HashSet<>(Collections.singletonList('i')), 2);
        Category c4 = new Category(new HashSet<>(Collections.singletonList('i')), 1);
        Category c5 = new Category(new HashSet<>(Arrays.asList('g', 'l', 'o')), 6);
        Category c6 = new Category(new HashSet<>(Collections.singletonList('o')), 2);
        Category c7 = new Category(new HashSet<>(Arrays.asList('c', 'g', 'i', 'l', 'o')), 5);
        return Arrays.asList(c1, c2, c3, c4, c5, c6, c7);
    }

    private List<List<Word>> createWordsFor1() {
        Set<Character> desiredChars = new HashSet<>(Arrays.asList('L', 'O', 'G', 'I', 'C')).stream().map(Character::toLowerCase).collect(Collectors.toSet());
        Word w1 = new Word("love", desiredChars);
        Word w2 = new Word("work", desiredChars);
        Word w3 = new Word("in", desiredChars);
        Word w4 = new Word("i", desiredChars);
        Word w5 = new Word("global", desiredChars);
        Word w6 = new Word("to", desiredChars);
        Word w7 = new Word("logic", desiredChars);
        return Stream.of(w1, w2, w3, w4, w5, w6, w7).map(Collections::singletonList).collect(Collectors.toList());
    }


    @Test
    public void createCategoryWordMapShouldReturnEmpty() {
        //given
        String s = "I love to work in global logic!";
        Set<Character> desiredChars = new HashSet<>(Arrays.asList('P', '1', 'X'));

        //when
        var sentence = new Sentence(s, desiredChars);
        Map<Category, List<Word>> expected = sentence.createCategoryWordMap();
        assertTrue(expected.isEmpty());
    }

    @Test
    public void createCategoryWordMapOneCategory() {
        //given
        String s = "plate level";
        Set<Character> desiredChars = new HashSet<>(Arrays.asList('l', 'o', 'g', 'i', 'c'));
        Word plate = new Word("plate", desiredChars);
        Word level = new Word("level", desiredChars);
        var words = Arrays.asList(plate, level);
        var category = new Category(new HashSet<>(Collections.singletonList('l')), 5);
        Map<Category, List<Word>> expected = new HashMap<>();
        expected.put(category, words);

        //when
        var sentence = new Sentence(s, desiredChars);

        //then

        assertEquals(expected, sentence.createCategoryWordMap());
    }
}
