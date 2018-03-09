package socket.chat.network.commands;

import chat.model.Message;
import chat.model.User;
import socket.chat.ClientContext;

/**
 * Created by affo on 08/03/18.
 */
public class MessageNotification implements Response {
    public final Message message;

    public MessageNotification(Message message) {
        this.message = message;
    }

    @Override
    public void handle(ResponseHandler handler) {
        User userToNotify = ClientContext.get().getCurrentUser();
        userToNotify.receiveMessage(message);
    }
}
