package utils;

import java.util.concurrent.atomic.AtomicInteger;

public class TimeFromStart {

    @SuppressWarnings("BusyWait")
    public static void countTimeFromStart() {
        AtomicInteger time = new AtomicInteger();
        Thread printSecondsFromStart = new Thread(() -> {
            while (true) {
                System.out.println(time.get());
                time.getAndIncrement();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread printMessageEvery5Seconds = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Пройшло 5 секунд");
            }
        });

        printSecondsFromStart.start();
        printMessageEvery5Seconds.start();
    }
}
