package utils;

import java.util.Arrays;
import java.util.Stack;

public class ValuesReplace {
    private static Object[] numbers;
    private static Stack<Object> stack = new Stack<>();
    private static int iterator = 0;

    public static void printNumbersWithReplace(int n) {
        numbers = new Object[n + 1];
        for (int i = 0; i <= n; i++) {
            numbers[i] = i;
        }

        //Arrays.stream(numbers).forEach(s -> System.out.print(s + " "));

        Thread A = new Thread(() -> {
            if ((int) numbers[iterator] % 3 == 0)
                stack.push("fizz");
        });

        Thread B = new Thread(() -> {
            if ((int) numbers[iterator] % 5 == 0)
                stack.push("buzz");
        });

        Thread D = new Thread(() -> {
            while (iterator <= n) {
                A.start();
                B.start();
                if (!stack.isEmpty()) {
                    System.out.print(stack.pop() + " ");
                } else System.out.print(numbers[iterator]);
                A.stop();
                B.stop();
                iterator++;
            }
        });


        D.start();

    }
}
