import java.lang.reflect.Field;
import java.util.Scanner;

public class Reflection {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        String literalStr = "Java";
        System.out.println("До зміни (літерал): " + literalStr);
        modifyString(literalStr, "Hello");
        System.out.println("Після зміни (літерал): " + literalStr);
        System.out.println();

        System.out.print("Введіть рядок: ");
        String inputStr = sc.nextLine();

        System.out.print("Введіть нове значення для заміни: ");
        String newValue = sc.nextLine();

        System.out.println("До зміни (введений): " + inputStr);
        modifyString(inputStr, newValue);
        System.out.println("Після зміни (введений): " + inputStr);
    }

    private static void modifyString(String target, String newValue) throws Exception {
        Field valueField = String.class.getDeclaredField("value");
        valueField.setAccessible(true);

        Object value = valueField.get(target);

        byte[] bytes = (byte[]) value;

        byte[] newBytes = newValue.getBytes();
        System.arraycopy(newBytes, 0, bytes, 0, Math.min(bytes.length, newBytes.length));
    }
}

