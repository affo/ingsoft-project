package socket.chat.network.commands;

import chat.model.Group;

/**
 * Created by affo on 08/03/18.
 */
public class JoinGroupResponse implements Response {
    public final Group group;

    public JoinGroupResponse(Group group) {
        this.group = group;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
