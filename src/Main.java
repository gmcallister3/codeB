import java.io.IOException;

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

            con.close();
        } catch (Exception e) {
            con.close();
            main(new String[0]);
            e.printStackTrace();
        }
    }
}
