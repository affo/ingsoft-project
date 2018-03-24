package socket.chat.network.commands;

public class CreateGroupRequest implements Request {
    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}