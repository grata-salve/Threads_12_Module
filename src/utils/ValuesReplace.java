package utils;

import java.util.Stack;

public class ValuesReplace {
    private static Integer[] numbers;
    private static Stack<Object> stack = new Stack<>();
    private static int iterator = 0;

    public static void printNumbersWithReplace(int n) {
        numbers = new Integer[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = i + 1;
        }

        Thread A = new Thread(() -> {
            synchronized (numbers) {
                for (Integer num : numbers) {
                    if (num != 0 && num % 3 == 0 && num % 5 != 0) {
                        stack.push("fizz");
                    }
                    try {
                        while (iterator != num) {
                            numbers.wait(0, 100);
                        }
                        numbers.notify();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread B = new Thread(() -> {
            synchronized (numbers) {
                for (Integer num : numbers) {
                    if (num != 0 && num % 5 == 0 && num % 3 != 0) {
                        stack.push("buzz");
                    }
                    try {
                        while (iterator != num) {
                            numbers.wait(0, 100);
                        }
                        numbers.notify();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread C = new Thread(() -> {
            synchronized (numbers) {
                for (Integer num : numbers) {
                    if (num != 0 && num % 5 == 0 && num % 3 == 0) {
                        stack.push("fizzbuzz");
                    }
                    try {
                        while (iterator != num) {
                            numbers.wait(0, 100);
                        }
                        numbers.notify();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread D = new Thread(() -> {
            A.start();
            B.start();
            C.start();
            while (iterator < n) {
                synchronized (stack) {
                    try {
                        stack.wait(30);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    number();
                    iterator++;
                }
            }
        });

        D.start();
    }

    private static void number() {
        if (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        } else {
            System.out.print(numbers[iterator] + " ");
        }
    }
}