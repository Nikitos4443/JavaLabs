import java.util.Scanner;

public class Main {

    private static final String DIRECTORY_TO_FIND = "/home/nikitos4443/IdeaProjects/JavaLabs/Lab8/files2";

    public static void main(String[] args) {
        FileExecutor fe = new FileExecutor();
        System.out.print("If you enter more than one symbol, program will get the first\nEnter the letter you want words to start with: ");
        Scanner sc = new Scanner(System.in);
        var letter = sc.nextLine().charAt(0);

        var map = fe.findWordsByLetter(letter, DIRECTORY_TO_FIND);

        System.out.println(map);
    }
}
