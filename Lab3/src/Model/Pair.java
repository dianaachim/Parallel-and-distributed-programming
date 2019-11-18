package Model;

public class Pair<T, S> {
    private T item1;
    private S item2;

    public Pair(T it1, S it2) {
        item1 = it1;
        item2 = it2;
    }


    public T getItem1() {
        return item1;
    }

    public void setItem1(T item1) {
        this.item1 = item1;
    }

    public S getItem2() {
        return item2;
    }

    public void setItem2(S item2) {
        this.item2 = item2;
    }
}
