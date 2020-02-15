import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pb2 {

    public static Queue<Integer> enqueueDigits(String number) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = number.length() - 1; i >=0; i--) {
            char character = number.charAt(i);
            queue.add(Integer.parseInt(Character.toString(character)));
        }
        return queue;
    }

    private static int dequeueOneDigit(Queue<Integer> queue) {
       return !queue.isEmpty() ? queue.remove() : 0;
    }

    private static String getNumberAsString(Queue<Integer> queue) {
        StringBuilder sb = new StringBuilder();
        for ( int digit : queue) {
            sb.append(digit);
        }
        return sb.reverse().toString();
    }

    private static Queue<Integer> addTwo(Queue<Integer> left, Queue<Integer> right) {
        Queue<Integer> result = new LinkedList<>();
        int carry = 0;

        while (!left.isEmpty() || !right.isEmpty()) {
            int sum = dequeueOneDigit(left) + dequeueOneDigit(right) + carry;
            result.add(sum % 10);
            carry = sum / 10;
        }
        if (carry != 0 ) {
            result.add(carry);
        }
        return result;
    }

    public static String getResult(List<String> numbers) throws InterruptedException {
        int size = numbers.size();
        int nrThreads = size - 1;

        Queue<Integer>[] queues = new Queue[size]; //list of queues
        for (int i=0; i<size; i++) {
            queues[i] = enqueueDigits(numbers.get(i));
        }

        ExecutorService service = Executors.newFixedThreadPool(nrThreads);

        for (int d = 0; d< Math.log(queues.length)/Math.log(2); d++) {
            int totalNumberOfTasks = (int) (queues.length / Math.pow(2, d+1));
            CountDownLatch latch = new CountDownLatch(totalNumberOfTasks);

            for ( int i = 0; i < queues.length; i+= Math.pow(2, d+1)) {
                final int iCopy = i;
                final int dCopy = d;

                service.execute(() -> {
                    final Queue<Integer> toAdd1 = queues[(int) (iCopy + Math.pow(2, dCopy) - 1)];
                    final Queue<Integer> toAdd2 = queues[(int) (iCopy + Math.pow(2, dCopy + 1) - 1)];
                    final Queue<Integer> addTwoResult = addTwo(toAdd1, toAdd2);

                    queues[(int) (iCopy + Math.pow(2, dCopy + 1) - 1)] = addTwoResult;

                    latch.countDown();
                });
            }
            latch.await();
        }
        service.shutdown();

        Queue<Integer> result = queues[queues.length - 1];
        return getNumberAsString(result);
    }
}
