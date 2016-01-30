import java.io.IOException;
import java.util.Random;
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
            while (true) {
                //Logic
                con.updateSecurities();
                for (int i = 0; i < 10; i++) {
                        String ticker = tickers[i];
                        Security sec = (Security) m.get(ticker);
                        initialP.put(ticker, sec.getMinAsk(0));
                    }
                for (int i = 0; i < 10; i++) {
                    String ticker = tickers[i];
                    Security sec = (Security) m.get(ticker);
                    laterP.put(ticker, sec.getMinAsk(3));
                }
                for (int i = 0; i < 10; i++) {
                    String ticker = tickers[i];
                    Security sec = (Security) m.get(ticker);
                    volatility = initialP.get(i) - laterP.get(i);
                    if (volatility > .1) {
                        buy(ticker, rand.nextInt(10), sec.getMinAsk(0)+.00001);
                    } else {
                        //sell(ticker, m.get(, sec.getMaxBid(0)+.00001)
                    }
                }
            }
            con.close();
        } catch (Exception e) {
            con.close();
            main(new String[0]);
            e.printStackTrace();
        }
    }
}
