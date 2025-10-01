package org.example;

import org.example.enums.SortBy;
import org.example.enums.SortOrder;
import org.example.files.Files;

public class Main {
    public static void main(String[] args) {
        Files files = new Files();

        //1
        System.out.println("======================================");
        var fileName = files.readFileName();
        var row = files.getRowWithMaxWords(fileName);
        System.out.println("The result: " + row);
        System.out.println("======================================\n");

        //3
        byte key = 5;
        System.out.println("======================================");
        System.out.print("Enter the string you want this program to encrypt: ");

        String encrypted = files.encryptInput(key);
        System.out.println("The result: " + encrypted);

        System.out.println("======================================\n");

        System.out.println("======================================");
        System.out.print("Enter the string you want this program to decrypt: ");
        String decrypted = files.decryptInput(key);
        System.out.println("The result: " + decrypted);
        System.out.println("======================================\n");

        //4
        System.out.println("======================================");
        var url = files.readURL();
        var tags = files.getSortedTags(url, SortBy.FREQUENCY, SortOrder.DESC);
        System.out.println(tags);
        System.out.println("======================================");
    }
}