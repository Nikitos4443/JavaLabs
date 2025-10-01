import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;

public class TestStrFinders {

    static IFindMinUniqCharsWord[] finders() {
        return new IFindMinUniqCharsWord[]{new StrFinder(), new StrFinderLambdaAndStreamApi()};
    }

    @ParameterizedTest
    @MethodSource("finders")
    public void testFindMinUniqCharsWord(IFindMinUniqCharsWord finder) {
        var result = finder.findMinUniqueCharsWord("abcd abcc aaaa");
        assert(Objects.equals(result, "aaaa"));

        var result2 = finder.findMinUniqueCharsWord("aaa bb c dddd");
        assert(Objects.equals(result2, "aaa"));

        var result3 = finder.findMinUniqueCharsWord("");
        assert(Objects.equals(result3, ""));
    }
}
