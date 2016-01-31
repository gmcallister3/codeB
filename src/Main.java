import java.io.IOException;
import java.util.Random;
import java.util.*;
/**
 * Created by Anthony on 1/30/2016.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //Stuff to login with
        String host = "codebb.cloudapp.net";
        int port = 17429;
        String username = "TheWolframOfWallstreet";
        String pword = "gm16aldy17";

        //Connect
        Connection con = new Connection(host, port, username, pword);
        Date time = new Date();
        long t = time.getTime();
        //Try
        try {
            //Scanner input = new Scanner(System.in);
            con.updateSecurities();
            String[] tickers = con.getSecurities();
            Map m = con.getSecurityMap(); 
            HashMap<String, Double> initialP = new HashMap<>();
            HashMap<String, Double> laterP = new HashMap<>();
            long in20Minutes = 20 * 60 * 1000;
            Random rand = new Random();
            for (int i = 0; i < tickers.length; i++) {
                Security sec = (Security) m.get(tickers[i]);
                if (sec.getMinAsk(0) < 12) {
                    con.buy(tickers[i], 20, sec.getMinAsk(0)-.0001);
                }
            } 
            while (true) {
                if (time.getTime() - t > 10 * 60 * 1000) {
                    Security maxSec = (Security) m.get(tickers[0]);
                    for (int i = 0; i < tickers.length; i++) {
                        String ticker = tickers[i];
                        Security sec = (Security) m.get(ticker);
                        if (maxSec.getNet_worth().getFirst() < sec.getNet_worth().getFirst()) {
                            maxSec = sec;
                        }
                    }
                    con.buy(maxSec.getSymbol(), 10, maxSec.getMinAsk(0)+.00001);

                }
                //Logic
                con.updateSecurities();
                for (int i = 0; i < 10; i++) {
                    String ticker = tickers[i];
                    Security sec = (Security) m.get(ticker);
                    initialP.put(ticker, sec.getMinAsk(0));
                    }
                for (int i = 0; i < tickers.length; i++) {
                    String ticker = tickers[i];
                    Security sec = (Security) m.get(ticker);
                    laterP.put(ticker, sec.getMinAsk(3));
                }
                for (int i = 0; i < tickers.length; i++) {
                    String ticker = tickers[i];
                    Security sec = (Security) m.get(ticker);
                    double volatility = initialP.get(ticker) - laterP.get(ticker);
                    if (volatility > .1) {
                        con.buy(ticker, rand.nextInt(5), sec.getMinAsk(0)+.00001);
                    } else {
                        con.sell(ticker, rand.nextInt(5), sec.getMaxBid(0)-.00001);
                    }
                }
            }
            //con.close();
        } catch (Exception e) {
            e.printStackTrace();
            con.close();
            main(new String[0]);
        }
    }
}
