import java.util.Scanner;

public class Ex5_InverterCaracteres {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite uma string para inverter: ");
        String input = scanner.nextLine();

        scanner.close();

        String result = invertString(input);

        System.out.println("String invertida: " + result);
    }

    public static String invertString(String str) {
        char[] chars = new char[str.length()];

        for (int i = 0; i < str.length(); i++) {
            chars[i] = str.charAt(str.length() - 1 - i);
        }

        String inverted = "";
        for (char c : chars) {
            inverted += c;
        }

        return inverted;
    }
}
