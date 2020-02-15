import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int NUMBER_LIST_SIZE = 8;
    private static final int NUMBER_STRING_LIST_SIZE = 8;

    public static void main(String[] args) throws InterruptedException {
        long start2 = System.nanoTime();
        problem1();
        long end2 = System.nanoTime();
        System.out.println("Time: " + (end2 - start2) / 100000 + "ms");
    }

    private static void problem1() throws InterruptedException {
        int[] numbers = generateNumberArray();

        System.out.println("Generated list:");
        Arrays.stream(numbers).forEach(n -> System.out.print(n + " "));
        System.out.println();

        int[] result = Pb1.getResult(numbers, 4);

        System.out.println("Sum result:");
        Arrays.stream(result).forEach(n -> System.out.print(n + " "));
        System.out.println();
    }

    private static void problem2() {
        try {
            List<String> numbers = generateNumberStringArray(NUMBER_STRING_LIST_SIZE);

            System.out.println("\nGenerated numbers:");
            numbers.forEach(n -> System.out.printf("%20s %n", n));

            String result = Pb2.getResult(numbers);

            System.out.println("Sum result:");
            System.out.printf("%20s %n", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<String> generateNumberStringArray(int size) {
        Random random = new Random();
        List<String> numbers = new ArrayList<>();

        for (int j = 0; j < size; j++) {
            StringBuilder number = new StringBuilder();

            int numberLength = random.nextInt(15) + 5;

            //generate digits
            for (int i = 0; i < numberLength; i++) {
                number.append(random.nextInt(10));
            }
            numbers.add(number.toString());
        }
        return numbers;
    }

    private static int[] generateNumberArray() {
        Random random = new Random();
        int[] s = new int[NUMBER_LIST_SIZE];

        for (int i = 0; i < NUMBER_LIST_SIZE; i++) {
            s[i] = random.nextInt(20);
        }
        return s;
    }
}
