package socket.chat.network.commands;

/**
 * Created by affo on 08/03/18.
 *
 * A method for every possible Response
 */
public interface ResponseHandler {
    void handle(TextResponse textResponse);

    void handle(JoinGroupResponse joinGroupResponse);

    void handle(UserCreatedResponse userCreatedResponse);
}
