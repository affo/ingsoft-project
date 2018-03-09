package socket.chat.network.commands;

import chat.model.Message;

/**
 * Created by affo on 08/03/18.
 */
public class SendMessageRequest implements Request {
    public final Message message;

    public SendMessageRequest(Message message) {
        this.message = message;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
