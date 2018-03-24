package socket.chat.network.commands;

import chat.model.Group;

public class GroupCreatedResponse implements Response {
    public final Group group;

    public GroupCreatedResponse(Group group) {
        this.group = group;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}