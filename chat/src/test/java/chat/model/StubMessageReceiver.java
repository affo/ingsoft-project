package chat.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by affo on 05/03/18.
 */
public class StubMessageReceiver implements MessageReceivedObserver {
    private List<Message> received = new LinkedList<>();

    @Override
    public void onMessage(Message message) {
        received.add(message);
    }

    public Message get() throws IndexOutOfBoundsException {
        return received.remove(0);
    }
}
