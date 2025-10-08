package org.example.files;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

import org.example.enums.SortOrder;
import org.example.enums.SortBy;
import org.example.files.comparators.TagsAlphabetComparator;
import org.example.files.comparators.TagsFrequencyComparator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Files {

    private static final Logger logger = Logger.getLogger(Files.class.getName());

    private final String defaultPath = System.getProperty("user.dir") + "/src/main/java/org/example/files/txtFiles/";

    static {
        try {
            SimpleFormatter formatter = new SimpleFormatter();

            FileHandler fileHandler = new FileHandler("logs/app.log", true);
            fileHandler.setFormatter(formatter);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(formatter);

            logger.setLevel(Level.INFO);
            consoleHandler.setLevel(Level.INFO);
            fileHandler.setLevel(Level.INFO);

            logger.addHandler(consoleHandler);
            logger.addHandler(fileHandler);

            logger.setUseParentHandlers(false);

        } catch (IOException e) {
            System.err.println("Logger init failed: " + e.getMessage());
        }
    }

    public String readFileName(String string) {
        logger.info("Запит імені файлу від користувача...");
        System.out.println();
        System.out.print(string);
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        try {
            String fileName = r.readLine();
            logger.fine("Отримано ім'я файлу: " + fileName);
            return fileName;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Помилка при зчитуванні імені файлу", e);
            throw new RuntimeException(e);
        }
    }

    public String readURL(String string) {
        logger.info("Запит URL від користувача...");
        System.out.println();
        System.out.print(string);
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        try {
            String url = r.readLine();
            logger.fine("Отримано URL: " + url);
            return url;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Помилка при зчитуванні URL", e);
            throw new RuntimeException(e);
        }
    }

    public String getRowWithMaxWords(String fileName) {
        logger.info("Пошук рядка з найбільшою кількістю слів у файлі " + fileName);

        try (BufferedReader r = new BufferedReader(
                new FileReader(defaultPath + fileName))) {

            String currentLine;
            String maxLine = null;
            int maxWords = 0;

            while ((currentLine = r.readLine()) != null) {
                int currentMaxWords = checkWordsAmount(currentLine);
                if (currentMaxWords > maxWords) {
                    maxWords = currentMaxWords;
                    maxLine = currentLine;
                }
            }

            logger.info("Рядок з найбільшою кількістю слів: \"" + maxLine + "\" (" + maxWords + " слів)");
            return maxLine;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Помилка при читанні файлу " + fileName, e);
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
        logger.fine("Шифрування введення з ключем " + key);
        return encryptOrDecryptInput(key, true);
    }

    public String decryptInput(byte key) {
        logger.fine("Дешифрування введення з ключем " + key);
        return encryptOrDecryptInput(key, false);
    }

    private String encryptOrDecryptInput(byte key, boolean isEncrypt) {
        StringBuilder sb = new StringBuilder();
        CypherInputStream filterStream = new CypherInputStream(System.in, key, isEncrypt);

        try {
            for (int i = filterStream.read(); i != -1; i = filterStream.read()) {
                sb.append((char) i);
            }
            logger.info((isEncrypt ? "Шифрування" : "Дешифрування") + " виконано успішно");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Помилка при " + (isEncrypt ? "шифруванні" : "дешифруванні"), e);
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    public List<Map.Entry<String, Integer>> getSortedTags(String pageURL, SortBy sortBy, SortOrder sortOrder) {
        logger.info("Отримання та сортування тегів зі сторінки " + pageURL);
        var tags = getTags(pageURL);

        List<Map.Entry<String, Integer>> sorted = switch (sortBy) {
            case ALPHABET -> sortTags(tags, new TagsAlphabetComparator(), sortOrder);
            case FREQUENCY -> sortTags(tags, new TagsFrequencyComparator(), sortOrder);
        };

        logger.info("Сортування завершено (" + sortOrder + ")");
        return sorted;
    }

    private List<Map.Entry<String, Integer>> sortTags(HashMap<String, Integer> tagsMap,
                                                      Comparator<Map.Entry<String, Integer>> comparator,
                                                      SortOrder order) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(tagsMap.entrySet());
        if (order == SortOrder.ASC) entries.sort(comparator);
        else entries.sort(comparator.reversed());
        return entries;
    }

    private HashMap<String, Integer> getTags(String pageURL) {
        var content = getAllContentByURL(pageURL);
        Document doc = Jsoup.parse(content);
        Elements elements = doc.getAllElements();

        var map = new HashMap<String, Integer>();
        for (Element element : elements) {
            String name = element.tagName();
            map.put(name, map.getOrDefault(name, 0) + 1);
        }

        logger.info("Отримано " + map.size() + " тегів зі сторінки");
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
                logger.fine("Вміст сторінки успішно отримано");
                return sb.toString();
            }

        } catch (IOException | URISyntaxException e) {
            logger.log(Level.SEVERE, "Помилка при зчитуванні сторінки: " + pageURL, e);
            throw new RuntimeException(e);
        }
    }
}
