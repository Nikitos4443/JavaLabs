package Dictionary;

import java.util.HashMap;

public class EnglishUkrainianTranslator {
    private HashMap<String, String> dictionary;

    public EnglishUkrainianTranslator() {
        dictionary = new HashMap<>();
    }

    public void addPair(String english, String ukrainian) {
        dictionary.put(english.toLowerCase(), ukrainian);
    }

    public String translatePhrase(String phrase) {
        StringBuilder translation = new StringBuilder();
        String[] words = phrase.split("\\s+");

        for (String word : words) {
            String lowerWord = word.toLowerCase();
            if (dictionary.containsKey(lowerWord)) {
                translation.append(dictionary.get(lowerWord));
            } else {
                translation.append(word);
            }
            translation.append(" ");
        }
        return translation.toString().trim();
    }
}