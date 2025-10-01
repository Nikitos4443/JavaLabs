import java.util.HashSet;

public class StrFinder implements IFindMinUniqCharsWord{

    public String findMinUniqueCharsWord(String words) {
        var wordArray = words.split("\\s+");

        var minLength = Integer.MAX_VALUE;
        var minLengthIndex = 0;

        for (int i = 0; i < wordArray.length; i++) {

            int len;
            if((len = calculateUniqueChars(wordArray[i])) == 1) {
                return wordArray[i];
            }

            if (len < minLength) {
                minLength = len;
                minLengthIndex = i;
            }
        }

        return wordArray[minLengthIndex];
    }

    private int calculateUniqueChars(String word) {
        HashSet<Character> set = new HashSet<>();

        for (int i = 0; i < word.length(); i++) {
            set.add(word.charAt(i));
        }

        return set.size();
    }
}
