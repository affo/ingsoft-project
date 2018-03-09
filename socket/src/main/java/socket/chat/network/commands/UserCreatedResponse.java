package socket.chat.network.commands;

import chat.model.User;

/**
 * Created by affo on 08/03/18.
 */
public class UserCreatedResponse implements Response {
    public final User user;

    public UserCreatedResponse(User user) {
        this.user = user;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
