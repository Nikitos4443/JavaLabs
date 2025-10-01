package org.example.files;

import java.io.*;
import java.net.*;
import java.util.*;

import org.example.enums.SortOrder;
import org.example.files.comparators.TagsAlphabetComparator;
import org.example.enums.SortBy;
import org.example.files.comparators.TagsFrequencyComparator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Files {

    private final String defaultPath = System.getProperty("user.dir") + "/src/main/java/org/example/files/txtFiles/";

    public String readFileName() {
        System.out.print("Enter file name: ");
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        try{
            return r.readLine();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String readURL() {
        System.out.print("Enter url to get tags count: ");
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        try{
            return r.readLine();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getRowWithMaxWords(String fileName) {
        try (BufferedReader r = new BufferedReader(
                new FileReader(defaultPath + fileName))) {

            String currentLine;
            String maxLine = null;
            int maxWords = 0;

            while ((currentLine = r.readLine()) != null) {
                int currentMaxWords;
                if((currentMaxWords = checkWordsAmount(currentLine)) > maxWords) {
                    maxWords = currentMaxWords;
                    maxLine = currentLine;
                }
            }

            return maxLine;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int checkWordsAmount(String line) {
        var words = line.split("[^a-zA-Z0-9]+");
        int count = 0;

        for (String word : words) {
            if (word.length() >= 2) {
                count++;
            }
        }
        return count;
    }

    public String encryptInput(byte key) {
        return encryptOrDecryptInput(key, true);
    }

    public String decryptInput(byte key) {
        return encryptOrDecryptInput(key, false);
    }

    private String encryptOrDecryptInput(byte key, boolean isEncrypt) {
        StringBuilder sb = new StringBuilder();
        CypherInputStream filterStream = new CypherInputStream(System.in, key, isEncrypt);

        try{
            for(int i = filterStream.read(); i != -1; i = filterStream.read()) {
                sb.append((char)i);
            }
        }catch(IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    public List<Map.Entry<String, Integer>> getSortedTags(String pageURL, SortBy sortBy, SortOrder sortOrder) {
        var tags = getTags(pageURL);

        return switch (sortBy) {
            case ALPHABET -> sortTags(tags, new TagsAlphabetComparator(), sortOrder);
            case FREQUENCY -> sortTags(tags, new TagsFrequencyComparator(), sortOrder);
        };
    }

    private List<Map.Entry<String, Integer>> sortTags(HashMap<String, Integer> tagsMap, Comparator<Map.Entry<String, Integer>> comparator, SortOrder order) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(tagsMap.entrySet());

        if(order == SortOrder.ASC) entries.sort(comparator);
        else entries.sort(comparator.reversed());

        return entries;
    }

    private HashMap<String, Integer> getTags(String pageURL) {
        var content = getAllContentByURL(pageURL);

        Document doc = Jsoup.parse(content);
        Elements elements = doc.getAllElements();

        var map = new HashMap<String, Integer>();
        for(Element element : elements) {
            String name = element.tagName();
            if(map.containsKey(name)) {
                map.replace(name, map.get(name) + 1);
            } else {
                map.put(name, 1);
            }
        }

        return map;
    }

    private String getAllContentByURL(String pageURL) {
        try {
            URI uri = new URI(pageURL);
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();

            try (InputStream is = new BufferedInputStream(connection.getInputStream());
                 BufferedReader in = new BufferedReader(new InputStreamReader(is))) {

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
