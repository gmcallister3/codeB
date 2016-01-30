import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anthony on 1/30/2016.
 */
public class Connection {
    Map<String, Security> map = new HashMap<>();
    Socket socket;
    PrintWriter pout;
    BufferedReader bin;

    public Connection(String host, int port, String username, String password) throws IOException {
        socket = new Socket(host, port);
        pout = new PrintWriter(socket.getOutputStream());
        bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        pout.println(username + " " + password);
    }

    public int getCash() throws IOException {
        pout.println("MY_CASH");
        pout.flush();

        System.out.println("\nGetting cash");
        while (bin.ready()) {
            String line = bin.readLine();
            System.out.println("Recieved message: " + line);
            if(line.substring(0,12).equals("MY_CASH_OUT "))
                return Integer.parseInt(line.substring(12));
        }

        return -1;
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
}
