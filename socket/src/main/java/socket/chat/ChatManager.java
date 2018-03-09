package socket.chat;

import chat.model.Group;
import chat.model.User;
import socket.chat.exceptions.InvalidUsernameException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by affo on 27/02/18.
 *
 * SINGLETON
 * It is still a piece of the model that traces the instances available.
 */
public class ChatManager {
    private static ChatManager instance;
    private final Group defaultGroup;
    private Set<Group> groups = new HashSet<>();
    private Set<User> users = new HashSet<>();

    private ChatManager() {
        // create one group by default
        defaultGroup = createGroup();
    }

    public static synchronized ChatManager get() {
        if (instance == null) {
            instance = new ChatManager();
        }

        return instance;
    }

    public synchronized Group getDefaultGroup() {
        return defaultGroup;
    }

    public synchronized Set<Group> groups() {
        return new HashSet<>(groups);
    }

    public synchronized Set<User> users() {
        return new HashSet<>(users);
    }

    public synchronized Group createGroup() {
        Group group = new Group();
        groups.add(group);
        return group;
    }

    public synchronized User createUser(String name) throws InvalidUsernameException {
        User user = new User(name);

        if (users.contains(user)) {
            throw new InvalidUsernameException("Username already in use: " + name);
        }

        users.add(user);
        return user;
    }
}
