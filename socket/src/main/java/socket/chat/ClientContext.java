package socket.chat;

import chat.model.Group;
import chat.model.User;

/**
 * Created by affo on 09/03/18.
 *
 * SINGLETON (CLIENT SIDE)
 *
 * Context used at any client to records current user and group
 */
public class ClientContext {
    private static ClientContext instance;
    private User currentUser;
    private Group currentGroup;

    private ClientContext() {
    }

    public static synchronized ClientContext get() {
        if (instance == null) {
            instance = new ClientContext();
        }

        return instance;
    }

    public synchronized User getCurrentUser() {
        return currentUser;
    }

    public synchronized void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public synchronized Group getCurrentGroup() {
        return currentGroup;
    }

    public synchronized void setCurrentGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }
}
