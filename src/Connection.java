import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anthony on 1/30/2016.
 */
public class Connection {
    Map<String, Security> securities = new HashMap<>();
    Socket socket;
    PrintWriter pout;
    BufferedReader bin;

    public Connection(String host, int port, String username, String password) throws IOException, InterruptedException {
        socket = new Socket(host, port);
        pout = new PrintWriter(socket.getOutputStream());
        bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        pout.println(username + " " + password);

        //Initialize each security

        pout.println("SECURITIES");
        pout.flush();

        System.out.println("\nPopulating Securities");
        for(int trial = 0; trial < 100; trial ++) {
            while (bin.ready()) {
                String line = bin.readLine();
                System.out.println("Recieved message: " + line);
                if(line.length() > 15 && line.substring(0,15).equals("SECURITIES_OUT ")) {
                    trial=100;
                    String[] strs = line.substring(15).split(" ");
                    for(int i = 0; i < strs.length; i+=4) {
                        String ticker = strs[i];
                        double net_worth = Double.parseDouble(strs[i+1]);
                        double dividend_ratio = Double.parseDouble(strs[i+2]);
                        double volatility = Double.parseDouble(strs[i+3]);

                        securities.put(ticker, new Security(ticker, volatility, dividend_ratio));
                        securities.get(ticker).push(net_worth,0.0,0, new ArrayList());
                    }
                }
            }
            Thread.sleep(10);
        }
    }

    public double getCash() throws IOException, InterruptedException {
        pout.println("MY_CASH");
        pout.flush();

        System.out.println("\nGetting cash");
        for(int i = 0; i < 100; i++) {
            while (bin.ready()) {
                String line = bin.readLine();
                System.out.println("Recieved message: " + line);
                if (line.length() > 12 && line.substring(0, 12).equals("MY_CASH_OUT "))
                    return Double.parseDouble(line.substring(12));
            }
            Thread.sleep(10);
        }

        return -1;
    }

    public void updateSecurities() throws IOException, InterruptedException {
        pout.println("SECURITIES");
        pout.flush();

        System.out.println("\nGetting Securities");
        for(int trial = 0; trial < 100; trial++) {
            while (bin.ready()) {
                String line = bin.readLine();
                System.out.println("Recieved message: " + line);
                if(line.length() > 15 && line.substring(0,15).equals("SECURITIES_OUT ")) {
                    String[] strs = line.substring(15).split(" ");
                    for(int i = 0; i < strs.length; i+=4) {
                        String ticker = strs[i];
                        double net_worth = Double.parseDouble(strs[i+1]);

                        securities.get(ticker).getNet_worth().push(net_worth);
                    }
                }
            }
            Thread.sleep(10);
        }
    }


    public void close() throws IOException {
        pout.println("CLOSE_CONNECTION");
        pout.flush();

        System.out.println("\nClosing");
        String line;

        while (bin.ready()) {
            System.out.println("Recieved message: " + bin.readLine());
        }

        pout.close();
        bin.close();

        System.out.println("Connection Closed");
    }

    public String[] getSecurities() {
        return securities.keySet().toArray(new String[securities.size()]);
    }

    public Map getSecurityMap() {
        return securities;
    }
}
