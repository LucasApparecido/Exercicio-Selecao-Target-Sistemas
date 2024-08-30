import java.util.Scanner;

public class Ex2_Fibonacci {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite um número para verificar se pertence à sequência de Fibonacci: ");
        int number = scanner.nextInt();

        if (isFibonacci(number)) {
            System.out.println(number + " pertence à sequência de Fibonacci.");
        } else {
            System.out.println(number + " não pertence à sequência de Fibonacci.");
        }

        scanner.close();
    }

    public static boolean isFibonacci(int number) {
        if (number < 0) {
            return false;
        }

        int firstValue = 0;
        int secondValue = 1;

        if (number == firstValue || number == secondValue) {
            return true;
        }

        while (secondValue <= number) {
            int temp = firstValue + secondValue;
            firstValue = secondValue;
            secondValue = temp;

            if (secondValue == number) {
                return true;
            }
        }

        return false;
    }
}
