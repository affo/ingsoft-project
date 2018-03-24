package chat.model;

import chat.exceptions.DuplicateEntityException;
import chat.exceptions.UserNotInGroupException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by affo on 27/02/18.
 */
public class Group implements Serializable {
    private static int uniqueGroupID = 0;
    private int groupID;
    private String groupName;
    private Set<User> users = new HashSet<>();
    private List<Message> messages = new LinkedList<>();
    private transient List<GroupChangeListener> listeners = new LinkedList<>();

    public Group() {
        super();
        groupID = uniqueGroupID;
        groupName = "group" + groupID;
        uniqueGroupID++;
    }

    private void checkUserInGroup(User user) throws UserNotInGroupException {
        if (!users.contains(user)) {
            throw new UserNotInGroupException(user, this);
        }
    }

    public int size() {
        return users.size();
    }

    public void sendMessage(Message message) throws UserNotInGroupException {
        checkUserInGroup(message.getSender());
        messages.add(message);

        for (User user : users) {
            if (!user.getUsername().equals(message.getSender().getUsername()))
                user.receiveMessage(message);
        }
    }

    public String getName() {
        return groupName;
    }

    public void setName(String name) {
        this.groupName = name;
    }

    public void join(User user) throws DuplicateEntityException {
        if (users.contains(user)) {
            throw new DuplicateEntityException("User " + user.getUsername() + " joined the group twice");
        }

        users.add(user);

        for (GroupChangeListener listener : listeners) {
            listener.onJoin(user);
        }
    }

    public void leave(User user) throws UserNotInGroupException {
        checkUserInGroup(user);
        users.remove(user);

        for (GroupChangeListener listener : listeners) {
            listener.onLeave(user);
        }
    }

    public boolean in(User user) {
        return users.contains(user);
    }

    public Set<User> users() {
        return new HashSet<>(users);
    }

    public void observe(GroupChangeListener listener) {
        listeners.add(listener);
    }

    public List<Message> messages() {
        return new LinkedList<>(messages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group users = (Group) o;

        return groupID == users.groupID;
    }

    @Override
    public int hashCode() {
        return groupID;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // upon deserialization, observers are reset
        listeners = new LinkedList<>();
    }
}
