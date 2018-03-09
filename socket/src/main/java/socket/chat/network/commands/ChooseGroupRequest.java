package socket.chat.network.commands;

/**
 * Created by affo on 09/03/18.
 */
public class ChooseGroupRequest implements Request {
    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
