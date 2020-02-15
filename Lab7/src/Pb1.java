import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pb1 {
    public static int[] getResult(int[] numbers, int threads) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threads);

        // bottom up
        int size = numbers.length;

        for (int d = 0; d < Math.log(size) / Math.log(2); d++) {
            int tasksNumber = (int) (size / Math.pow(2, d + 1));
            CountDownLatch latch = new CountDownLatch(tasksNumber);

            for (int i = 0; i < size; i += Math.pow(2, d + 1)) {
                final int copyI = i;
                final int copyD = d;

                service.execute(() -> {
                    final int n1 = numbers[(int) (copyI + Math.pow(2, copyD) - 1)];
                    final int n2 = numbers[(int) (copyI + Math.pow(2, copyD + 1) - 1)];
                    numbers[(int) (copyI + Math.pow(2, copyD + 1) - 1)] = n1 + n2;
                    latch.countDown();
                });
            }
            latch.await();
        }

        // top down
        numbers[size - 1] = 0;

        for (int d = (int) (Math.log(size) / Math.log(2) - 1); d >= 0; d--) {
            int taskNumber = (int) (size / Math.pow(2, d + 1));
            CountDownLatch latch = new CountDownLatch(taskNumber);

            for (int i = 0; i < size; i += Math.pow(2, d + 1)) {
                final int copyI = i;
                final int copyD = d;

                service.execute(() -> {
                    final int aux = numbers[(int) (copyI + Math.pow(2, copyD)) - 1];
                    numbers[(int) (copyI + Math.pow(2, copyD)) - 1] = numbers[(int) (copyI + Math.pow(2, copyD + 1)) - 1];
                    numbers[(int) (copyI + Math.pow(2, copyD + 1)) - 1] += aux;
                    latch.countDown();
                });
            }
            latch.await();
        }
        service.shutdownNow();
        return numbers;
    }
}
