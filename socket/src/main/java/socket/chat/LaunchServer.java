package socket.chat;


import socket.chat.network.ChatServer;

import java.io.IOException;

/**
 * Created by affo on 08/03/18.
 */
public class LaunchServer {
    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer(8000);

        try {
            server.run();
        } finally {
            server.close();
        }
    }
}
