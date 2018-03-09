package socket.chat.network.commands;

import java.io.Serializable;

/**
 * Created by affo on 08/03/18.
 */
public interface Request extends Serializable {
    Response handle(RequestHandler handler);
}
