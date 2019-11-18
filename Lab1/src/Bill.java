import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Bill {
    private HashMap<Product, Integer> products;
    private float totalPrice;
    private Semaphore s = new Semaphore(1, true);

    public Bill(HashMap<Product, Integer> list) {
        products = list;
        totalPrice = computeTotalPrice();
    }

    public Bill() {
        products = new HashMap<>();
        totalPrice = 0;
    }

    public void lock() {
        try {
            s.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unlock() {
        try {
            s.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float computeTotalPrice() {
        float sum = 0;
        for (Map.Entry<Product, Integer> e : products.entrySet()) {
            sum += e.getValue() * e.getKey().getPrice();
        }
        return sum;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getProductQuantity(int ID) {
        int quantity = 0;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            if (entry.getKey().getID() == ID) {
                quantity += entry.getValue();
            }
        }
        return quantity;
    }

    public boolean addItem(Product product, Integer quantity) {
        if (this.products.containsKey(product))
            return false;
        this.products.put(product, quantity);
        this.totalPrice += product.getPrice() * quantity;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Product product : this.products.keySet()) {
            result.append(product.getID())
                    .append(" - ")
                    .append(product.getQuantity())
                    .append("\n");
        }
        return result.toString();
    }
}
