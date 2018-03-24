package socket.chat.network.commands;

public class GetGroupsRequest implements Request{
    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}