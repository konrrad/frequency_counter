package printer;

import lombok.Data;
import sentence.Category;
import sentence.Sentence;
import sentence.Word;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ConsolePrinter implements SentencePrinter {
    protected StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void print(Sentence sentence) {
        String toPrint = buildStringFromSentence(sentence);
        System.out.println(toPrint);
    }

    protected String buildStringFromSentence(Sentence sentence) {
        stringBuilder.setLength(0);
        var categoryWordsMap = sentence.createCategoryWordMap();
        final var totalNumberOfDesiredCharacters = getTotalNumberOfDesiredCharacters(categoryWordsMap.entrySet());
        List<CategoryFrequency> categoryFrequency = createSortedCategoryFrequency(categoryWordsMap);

        for (CategoryFrequency catFr : categoryFrequency) {
            Category category = catFr.getCategory();
            stringBuilder.append(String.format("%s = %.02f (%d/%d)\n", category, (double) catFr.getFrequency() / totalNumberOfDesiredCharacters, catFr.getFrequency(), totalNumberOfDesiredCharacters));
        }

        final var splitSequenceLength = sentence.getSplitSequenceLength();
        stringBuilder.append(String.format("TOTAL FREQUENCY: %.02f (%d/%d)", (double) totalNumberOfDesiredCharacters / splitSequenceLength, totalNumberOfDesiredCharacters, splitSequenceLength));
        return stringBuilder.toString();
    }

    private List<CategoryFrequency> createSortedCategoryFrequency(Map<Category, List<Word>> categoryListMap) {
        return categoryListMap.entrySet()
                .stream()
                .map(categoryListEntry -> {
                    final int desiredCharsFrequency = categoryListEntry.getValue()
                            .stream()
                            .map(Word::getNumOfDesiredChars)
                            .reduce(0, Integer::sum);
                    return new CategoryFrequency(categoryListEntry.getKey(), desiredCharsFrequency);
                })
                .sorted()
                .collect(Collectors.toUnmodifiableList());
    }


    protected int getTotalNumberOfDesiredCharacters(Set<Map.Entry<Category, List<Word>>> entries) {
        return entries
                .stream()
                .flatMap(categoryListEntry -> categoryListEntry.getValue().stream())
                .map(Word::getNumOfDesiredChars)
                .reduce(0, Integer::sum);
    }


    @Data
    private static class CategoryFrequency implements Comparable<CategoryFrequency> {
        private final Category category;
        private final int frequency;

        @Override
        public int compareTo(CategoryFrequency o) {
            return Integer.compare(frequency, o.frequency);
        }
    }
}
