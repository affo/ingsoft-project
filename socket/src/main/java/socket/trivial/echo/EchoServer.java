package socket.trivial.echo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by affo on 07/03/18.
 */
public class EchoServer implements Closeable {
    private final int port;
    private ServerSocket serverSocket;

    public EchoServer(int port) {
        this.port = port;
    }

    public void init() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println(">>> Listening on " + port);
    }

    public Socket acceptConnection() throws IOException {
        // blocking call
        Socket accepted = serverSocket.accept();
        System.out.println("Connection accepted: " + accepted.getRemoteSocketAddress());
        return accepted;
    }

    public void handleConnection(Socket clientConnection) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            // getting the streams for communication
            is = clientConnection.getInputStream();
            os = clientConnection.getOutputStream();

            // Decorator pattern ;)
            // BufferedReader is useful because it offers readLine
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            PrintWriter out = new PrintWriter(os);

            String msg;

            do {
                msg = in.readLine();

                if (msg != null && !msg.startsWith("quit")) {
                    System.out.println("<<< " + clientConnection.getRemoteSocketAddress() + ": " + msg);
                    out.println(">>> " + msg);
                    // when you call flush you really send what
                    // you added to the buffer with println.
                    out.flush();
                }
            } while (msg != null && !msg.startsWith("quit"));

        } finally {
            if (is != null && os != null) {
                is.close();
                os.close();
            }
            clientConnection.close();
        }
    }

    public void lifeCycle() throws IOException {
        init();

        try {
            Socket socket = acceptConnection();
            handleConnection(socket);
        } finally {
            close();
        }
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
