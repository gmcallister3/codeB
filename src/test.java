//import com.google.gson.Gson;

import java.io.*;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

/**
 * Created by Anthony on 1/30/2016.
 */
public class Test {
    public static void main(String args[]) throws IOException, InterruptedException {
        // String content = new String(readAllBytes(get("config.json")));
        // Config config = new Gson().fromJson(content, Config.class);
        String host = "codebb.cloudapp.net";
        int port = 17429;
        String username = "TheWolframOfWallstreet";
        String pword = "gm16aldy17";
        Connection con = new Connection(host, port, username, pword);

        try {
            System.out.println(con.getCash());
            con.sell("AAPL", 1, 1);
            con.close();
        } catch (Exception e) {
            con.close();
            e.printStackTrace();
        }

    }
}