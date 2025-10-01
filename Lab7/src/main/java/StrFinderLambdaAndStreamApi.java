import java.util.Comparator;
import java.util.List;

public class StrFinderLambdaAndStreamApi implements IFindMinUniqCharsWord {

    public String findMinUniqueCharsWord(String words) {
        List<String> wordsList = List.of(words.split("\\s+"));

        return wordsList
                .stream()
                .min(Comparator.comparingInt(word -> (int) word.chars().distinct().count()))
                .orElse("");
    };
}
