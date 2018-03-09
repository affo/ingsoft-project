package socket.chat.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by affo on 08/03/18.
 */
public class ChatServer {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newCachedThreadPool();
        System.out.println(">>> Listening on " + port);
    }

    public void run() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println(">>> New connection " + clientSocket.getRemoteSocketAddress());
            pool.submit(new ClientHandler(clientSocket));
        }
    }

    public void close() throws IOException {
        serverSocket.close();
        pool.shutdown();
    }
}
