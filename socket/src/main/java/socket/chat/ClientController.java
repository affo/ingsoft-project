package socket.chat;

import chat.model.Group;
import chat.model.Message;
import chat.model.User;
import socket.chat.network.Client;
import socket.chat.network.commands.*;

import java.io.IOException;

/**
 * Created by affo on 08/03/18.
 *
 * CLIENT-SIDE controller
 *
 * It holds a reference to the view for sending sudden responses.
 * It holds a reference to the networking layer.
 */
public class ClientController implements ResponseHandler {
    // reference to networking layer
    private final Client client;
    private Thread receiver;

    // the view
    private final View view;

    // Pieces of the model
    private User currentUser;
    private Group currentGroup;

    public ClientController(Client client) {
        this.client = client;
        this.view = new View(this);
    }

    /**
     *
     * @param username
     * @return the created user or null in case of failure
     */
    public User createUser(String username) {
        client.request(new CreateUserRequest(username));
        client.nextResponse().handle(this);
        return currentUser;
    }

    public Group chooseGroup() {
        client.request(new ChooseGroupRequest()); // dummy
        client.nextResponse().handle(this);
        return currentGroup;
    }

    public void startMessaging() {
        // start a receiver thread
        receiver = new Thread(
                () -> {
                    Response response;
                    do {
                        response = client.nextResponse();
                        if (response != null) {
                            response.handle(this);
                        }
                    } while (response != null);
                }
        );
        receiver.start();
    }

    public void sendMessage(String content) {
        client.request(
                new SendMessageRequest(new Message(currentGroup, currentUser, content)));
    }

    public void run() throws IOException {
        view.chooseUsernamePhase();
        view.chooseGroupPhase();
        view.messagingPhase();

        receiver.interrupt();
    }

    // -------------------------- Response handling

    @Override
    public void handle(TextResponse textResponse) {
        view.displayText(textResponse.toString());
    }

    @Override
    public void handle(JoinGroupResponse joinGroupResponse) {
        currentGroup = joinGroupResponse.group;
        ClientContext.get().setCurrentGroup(currentGroup);
    }

    @Override
    public void handle(UserCreatedResponse userCreatedResponse) {
        currentUser = userCreatedResponse.user;
        ClientContext.get().setCurrentUser(currentUser);
    }
}
