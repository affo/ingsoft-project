package chat.model;

import chat.exceptions.UserNotInGroupException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by affo on 27/02/18.
 */
public class User {
    private String username;
    private List<MessageReceivedObserver> observers;

    public User(String username) {
        super();
        this.username = username;
        this.observers = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void listenToMessages(MessageReceivedObserver observer) {
        observers.add(observer);
    }

    public void receiveMessage(Message message, Group group) {
        for (MessageReceivedObserver observer : observers) {
            observer.onMessage(group.getName(), message.getContent());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
