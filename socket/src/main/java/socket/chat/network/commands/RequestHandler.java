package socket.chat.network.commands;

/**
 * Created by affo on 08/03/18.
 *
 * A method for every possible Request
 */
public interface RequestHandler {
    Response handle(SendMessageRequest request);

    Response handle(CreateUserRequest request);

    Response handle(ChooseGroupRequest chooseGroupRequest);
}
