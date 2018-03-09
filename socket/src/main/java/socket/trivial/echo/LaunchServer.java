package socket.trivial.echo;

import java.io.IOException;

/**
 * Created by affo on 07/03/18.
 */
public class LaunchServer {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Provide port please");
            return;
        }

        int port = Integer.parseInt(args[0]);

        // TODO try to change to MultiEchoServer!
        EchoServer server = new EchoServer(port);
        server.lifeCycle();
    }
}
