import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

/**
 * Created by Anthony on 1/30/2016.
 */
public class Test {
    public static void main(String args[]) throws Exception {
        // String content = new String(readAllBytes(get("config.json")));
        // Config config = new Gson().fromJson(content, Config.class);
        String host = "codebb.cloudapp.net";
        int port = 17429;
        String username = "TheWolframOfWallstreet";
        String pword = "gm16aldy17";
        Connection con = new Connection(host, port, username, pword);

        //Check that the works, if not terminate
        try {
            System.out.println(con.getCash()); 
        } catch (IOException e) {
            con.close();
            e.printStackTrace();
        }

        //Test stock logic here
        Scanner input = new Scanner(System.in);
        con.updateSecurities();
        String[] tickers = con.getSecurities();
        Map m = con.getSecurityMap(); 
        HashMap<String, Double> initialP = new HashMap<>();
        HashMap<String, Double> laterP = new HashMap<>();

        Timer timer = new Timer();
        timer.schedule();
        while (true) {
            String strategy = input.nextLine();
            switch (strategy) {
                case "buy" :
                    //Look for most volatile stocks and then buy
                    for (int i = 0; i < 10; i++) {
                        String ticker = tickers[i];
                        Security sec = (Security) m.get(ticker);
                        initialP.put(ticker, sec.getMinAsk());
                    }
                    //Pause
                    for (int i = 0; i < 1000; i++) {

                    }
                    for (int i = 0; i < 10; i++) {
                        String ticker = tickers[i];
                        Security sec = (Security) m.get(ticker);
                        laterP.put(ticker, sec.getMinAsk());
                    }

                    break;
                case "money":
                    con.getCash();
                    break;
                case "close":
                    con.close();
                    System.exit(1);

            }
        }
        //con.close();
    }
}