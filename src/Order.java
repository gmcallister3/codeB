/**
 * Created by Anthony on 1/30/2016.
 */
public class Order {
    private Boolean bid;
    private double price;
    private int share;

    public Order(Boolean bid, double price, int share) {
        this.bid = bid;
        this.price = price;
        this.share = share;
    }

    public Boolean getBid() {
        return bid;
    }

    public double getPrice() {
        return price;
    }

    public int getShare() {
        return share;
    }
}
