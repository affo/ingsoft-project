package socket.chat.network.commands;

import chat.model.Group;

import java.util.Set;

public class GetGroupsResponse implements Response {
    public final Set<Group> groups;

    public GetGroupsResponse(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
