package chat.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by affo on 27/02/18.
 */
public class User implements Serializable {
    private String username;
    private transient List<MessageReceivedObserver> observers;

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

    public void receiveMessage(Message message) {
        for (MessageReceivedObserver observer : observers) {
            observer.onMessage(message);
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

    @Override
    public String toString() {
        return "@" + username;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // upon deserialization, observers are reset
        observers = new LinkedList<>();
    }
}
