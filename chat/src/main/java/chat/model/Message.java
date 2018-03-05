package chat.model;

import java.io.Serializable;

/**
 * Created by affo on 27/02/18.
 */
public class Message implements Serializable {
    private final String content;
    private final User user;

    public Message(User sender, String content) {
        this.content = content;
        this.user = sender;
    }

    public User getSender() {
        return user;
    }

    public String getContent() {
        return content;
    }
}
