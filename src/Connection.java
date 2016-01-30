import java.awt.image.BufferedImage;
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
    Map<String, Securities> map = new HashMap<>();
    Socket socket;
    PrintWriter pout;
    BufferedReader bin;

    public Connection(String host, int port, String username, String password) throws IOException {
        socket = new Socket(host, port);
        pout = new PrintWriter(socket.getOutputStream());
        bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        pout.println(username + " " + password);
    }

    public String getCash() throws IOException {
        pout.println("MY_CASH");
        String ret = "";
        while(bin.ready()) {
            ret+=bin.readLine();
        }
        return ret;
    }

    public void close() throws IOException {
        pout.println("CLOSE_CONNECTION");
        pout.flush();
        String line;
        while ((line = bin.readLine()) != null) {
            System.out.println(line);
        }
        pout.close();
        bin.close();
    }
}
