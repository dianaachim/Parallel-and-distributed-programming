import java.time.Instant;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        Random random = new Random();
        for (int idx = 0; idx<10; idx++) {
            Product product = new Product(idx, random.nextFloat()*100, random.nextInt(100));
            products.add(product);
        }

        Sale sale = new Sale(products);
        List<Thread> threads = new ArrayList<>();
        Instant start = Instant.now();

        for (int i = 0; i< 3; i++) {
            Thread t = new Thread(sale, Integer.toString(i));
            threads.add(t);
            t.start();
        }
        for (int i = 0; i<3; i++) {
            try {
                threads.get(i).join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sale.checkingAmount();
        System.exit(0);
    }

}