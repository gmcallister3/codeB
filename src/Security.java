import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Anthony on 1/30/2016.
 */
public class Security {
    private static String symbol;

    private static double volatility;
    private static double dividend_ratio;

    private LinkedList<Double> net_worth;

    private LinkedList<Double> dividend;
    private LinkedList<Integer> shares;

    private LinkedList<ArrayList<Order>> orders;

    public Security(String symbol, double volatility, double dividend_ratio) {
        this.symbol = symbol;
        this.volatility = volatility;
        this.dividend_ratio = dividend_ratio;

        net_worth = new LinkedList<>();
        dividend = new LinkedList<>();
        shares = new LinkedList<>();
        orders = new LinkedList<>();
    }

    public void push(Double net_worth, Double dividend, Integer shares, ArrayList orders) {
        this.net_worth.push(net_worth);
        this.dividend.push(dividend);
        this.shares.push(shares);
        this.orders.push(orders);
    }

    public static String getSymbol() {
        return symbol;
    }

    public static double getVolatility() {
        return volatility;
    }

    public static double getDividend_ratio() {
        return dividend_ratio;
    }

    public LinkedList<Double> getNet_worth() {
        return net_worth;
    }

    public LinkedList<Double> getDividend() {
        return dividend;
    }

    public LinkedList<Integer> getShares() {
        return shares;
    }

    public LinkedList<ArrayList<Order>> getOrders() {
        return orders;
    }
}
