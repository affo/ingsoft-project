package socket.chat.network.commands;

/**
 * Created by affo on 09/03/18.
 */
public class ChooseGroupRequest implements Request {
    public final String selectedGroupName;

    public ChooseGroupRequest(String selectedGroupName) {
        this.selectedGroupName = selectedGroupName;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
