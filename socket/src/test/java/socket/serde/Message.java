package socket.serde;

import java.io.Serializable;

/**
 * Created by affo on 08/03/18.
 */
public class Message implements Serializable {
    public final String content;

    public Message(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return ">>> " + content;
    }
}
