import com.google.gson.Gson;

import java.io.*;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

/**
 * Created by Anthony on 1/30/2016.
 */
public class Test {
    public static void main(String args[]) throws IOException {
        String content = new String(readAllBytes(get("config.json")));
        Config config = new Gson().fromJson(content, Config.class);
        Connection con = new Connection(config.getHost(), config.getPort(), config.getUsername(), config.getPassword());

        try {
            System.out.println(con.getCash());
            con.close();
        } catch (IOException e) {
            con.close();
            e.printStackTrace();
        }

    }
}