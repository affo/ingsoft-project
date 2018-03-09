package socket.trivial.echo;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by affo on 07/03/18.
 */
public class MultiEchoServer extends EchoServer {
    private ExecutorService pool;

    public MultiEchoServer(int port) {
        super(port);
        pool = Executors.newCachedThreadPool();
    }

    @Override
    public void lifeCycle() throws IOException {
        init();

        while (true) {
            final Socket socket = acceptConnection();

            // TODO as in the slides, try to encapsulate the logic of handle connection in a
            // TODO separate ClientHandler class
            pool.submit(() -> {
                try {
                    handleConnection(socket);
                } catch (IOException e) {
                    System.err.println("Problem with " + socket.getLocalAddress() + ": " + e.getMessage());
                }
            });
        }
    }
}
