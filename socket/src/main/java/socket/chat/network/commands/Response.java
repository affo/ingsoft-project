package socket.chat.network.commands;

import java.io.Serializable;

/**
 * Created by affo on 08/03/18.
 */
public interface Response extends Serializable {
    void handle(ResponseHandler handler);
}
