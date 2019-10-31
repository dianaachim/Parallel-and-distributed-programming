import java.util.concurrent.Semaphore;

public class Product {
    private int ID;
    private float price;
    private int quantity;
    private int initialQuantity;
    public Semaphore s = new Semaphore(1);

    public Product(int id, float p, int q) {
        ID = id;
        price = p;
        quantity = q;
        initialQuantity = q;
    }

    public Product() {}

    public void lock() {
        try {
            s.acquire();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void unlock() {
        try {
            s.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    @Override
    public String toString() {
        return "Product( ID: " + ID + ", price: " + price + ", quantity: " + quantity + ")";
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
