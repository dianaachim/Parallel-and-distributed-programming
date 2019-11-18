import java.util.*;
import java.util.concurrent.Semaphore;

public class Sale implements Runnable{
    private Semaphore mutexForAllSales = new Semaphore(1, true);
    private float totalAmount;
    private List<Bill> allSales;
    private List<Product> products;
    private List<Product> initialProducts;
    private CheckAmount checkAmount;

    public Sale(List<Product> prods) {
        products = prods;
        totalAmount = 0;
        allSales = new ArrayList<>();
        initialProducts = new ArrayList<>();
        for (Product product : prods )  {
            Product prod_copy = new Product(product.getID(), product.getPrice(), product.getQuantity());
            initialProducts.add(prod_copy);
        }
        checkAmount = new CheckAmount();
        Timer timer = new Timer();
        timer.schedule(checkAmount, 0, 20);

    }

    private void lock() {
        try {
            mutexForAllSales.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unlock() {
        try {
            mutexForAllSales.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void checkingAmount() {
        this.checkAmount.run();
    }

    private Product getInitialProduct(int pid) {
        for (Product prd : initialProducts) {
            if (prd.getID() == pid) {
                return prd;
            }
        }
        return new Product();
    }

    private List<Integer> getProductsNumbersRandom() { // generates a list of randomly selected product indexes, of random length
        Random random = new Random();
        List<Integer> random_indexes = new ArrayList<>(); // positions of products from the list of all products that will go in the bill
        int limit = random.nextInt(this.products.size() - 1); // the amount of individual products in the bill
        for (int idx = 0; idx < limit; idx++) {
            int index = random.nextInt(products.size() - 1); // random product
            random_indexes.add(index);
        }
        return random_indexes;
    }

    private void createNewBill() {
        Random random = new Random();
        List<Integer> random_prods_pos = getProductsNumbersRandom();
        Bill bill = new Bill();
        //lock();
        bill.lock();
        for (Integer idx : random_prods_pos) {
            Product prod = products.get(idx);
            if (prod.getQuantity() > 0) {
                int quantity = random.nextInt(prod.getQuantity());

                if (prod.getQuantity() - quantity >= 0) {
                    prod.lock();
                    boolean success = bill.addItem(prod, quantity);
                    if (success) {
                        prod.setQuantity(prod.getQuantity() - quantity);
                    }
                    prod.unlock();
                }
                else {
                    System.out.println("Product" + prod.getID() + "not in stock!");
                }
            }
        }
        if (!bill.getProducts().isEmpty()) {
            allSales.add(bill);
            totalAmount += bill.getTotalPrice();
        }
        //unlock();
        bill.unlock();
    }

    @Override
    public void run() {
        Random random = new Random();
        int nrOperations = random.nextInt(7);
        for (int i =0; i<nrOperations; i++) {
            createNewBill();
        }
    }

    private class CheckAmount extends TimerTask {
        // abstract class that defines a task that can be scheduled to run periodically

        @Override
        public void run() { // the check operation on the bills
            float amount = 0;
            lock();
            // check that the amount is correct
            for (Bill bill : allSales) {
                amount += bill.getTotalPrice();
            }
            boolean is_amount_correct = amount == totalAmount;
            if (is_amount_correct) {
                System.out.println("The amount is correct: " + totalAmount);
            } else {
                System.out.println("The amount is incorrect: " + totalAmount + " and should instead be: " + amount);
            }

            // check that the product quantities are correct
            for (Product product : products) {
                product.lock(); // compute the sold quantity for the product
                int soldQuantity = 0;
                for (Bill bill : allSales) {
                    soldQuantity += bill.getProductQuantity(product.getID());
                }
                int initialProductQuantity = getInitialProduct(product.getID()).getQuantity();
                int currentQuantity = product.getQuantity();
                if (currentQuantity + soldQuantity == initialProductQuantity) {
                    System.out.println("The product " + product.getID() + " with sold quantity: " + soldQuantity + " is correct; price: " + product.getPrice());
                } else {
                    System.out.println("The product " + product.getID() + " with sold quantity: " + soldQuantity + " is incorrect, should be: " + (initialProductQuantity - soldQuantity));
                }
                product.unlock();
            }


            unlock();
        }
    }

}

