package org.example;

import org.example.enums.SortBy;
import org.example.enums.SortOrder;
import org.example.files.Files;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Files files = new Files();
        Scanner scanner = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("Choose language / Виберіть мову:\n1. English\n2. Українська");
        System.out.print("> ");
        String langChoice = scanner.nextLine();

        Locale locale;
        if ("2".equals(langChoice)) {
            locale = Locale.of("uk");
        } else {
            locale = Locale.of("en");
        }

        ResourceBundle rb = ResourceBundle.getBundle(
                "location.messages",
                locale
        );
        System.out.println("======================================\n");

        boolean running = true;
        while (running) {
            System.out.println(rb.getString("menu.options"));
            System.out.print("> ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    String fileName = files.readFileName(rb.getString("prompt.filename"));
                    var row = files.getRowWithMaxWords(fileName);
                    System.out.println(rb.getString("result.row") + " " + row);
                    break;
                case "2":
                    byte key = 5;
                    System.out.print(rb.getString("prompt.stringEncrypt") + " ");
                    String encrypted = files.encryptInput(key);
                    System.out.println(rb.getString("result.encrypted") + " " + encrypted);
                    break;
                case "3":
                    System.out.print(rb.getString("prompt.stringDecrypt") + " ");
                    String decrypted = files.decryptInput((byte) 5);
                    System.out.println(rb.getString("result.decrypted") + " " + decrypted);
                    break;
                case "4":
                    String url = files.readURL(rb.getString("prompt.url"));
                    System.out.println();
                    var tags = files.getSortedTags(url, SortBy.FREQUENCY, SortOrder.DESC);
                    System.out.println(tags);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option / Невідома опція");
            }

            System.out.println();
        }

        System.out.println("Program exited. / Програма завершена.");
    }
}
