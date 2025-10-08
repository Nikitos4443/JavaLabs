import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FileExecutor {

    ConcurrentHashMap<String, List<String>> map;

    public ConcurrentHashMap<String, List<String>> findWordsByLetter(Character letter, String directoryPath) {
        map = new ConcurrentHashMap<>();
        try{
            findWordsRecursive(letter, directoryPath);
        }catch(InterruptedException e) {
            System.err.println("Error while trying to find words by letter");
        }
        writeMapToFile(directoryPath);
        return map;
    }

    private void findWordsRecursive(Character letter, String path) throws InterruptedException {
        File file = new File(path);
        if(file.isDirectory()) {
            List<Thread> threads = new ArrayList<>();
            for(File fileInDirectory: Objects.requireNonNull(file.listFiles())) {
                if(fileInDirectory.isDirectory()) {
                    Thread t = new Thread(() -> {
                        try {
                            findWordsRecursive(letter, fileInDirectory.getAbsolutePath());
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    t.start();
                    threads.add(t);
                } else {
                    findWordsRecursive(letter, fileInDirectory.getAbsolutePath());
                }
            }
            for(Thread t : threads) {
                t.join();
            }
        } else {
            if(!file.getName().toLowerCase().endsWith(".txt")) return;

            var wordsInFile = getWordsInFile(letter, file);
            map.put(path, wordsInFile);
        }
    }


    private List<String> getWordsInFile(Character letter, File file) {
        try(Scanner scanner = new Scanner(file)) {
            List<String> words = new ArrayList<>();

            while(scanner.hasNext()) {
                var line = scanner.nextLine();
                var allWordsOnLine = line.split("\\s+");

                for(var word : allWordsOnLine) {
                    if(word.toLowerCase().charAt(0) == Character.toLowerCase(letter)) {
                        words.add(word);
                    }
                }
            }

            return words;
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    private void writeMapToFile(String path) {
        try(FileWriter fileWriter = new FileWriter(path + "/../output.txt", false)) {
            fileWriter.write(map.toString());
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
