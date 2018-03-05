package chat.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by affo on 05/03/18.
 */
public class StubMessageReceiver implements MessageReceivedObserver {
    private List<Received> received = new LinkedList<>();

    @Override
    public void onMessage(String groupName, String messageBody) {
        received.add(new Received(groupName, messageBody));
    }

    public Received get() throws IndexOutOfBoundsException {
        return received.remove(0);
    }

    public static class Received {
        public final String groupName, messageBody;

        private Received(String groupName, String messageBody) {
            this.groupName = groupName;
            this.messageBody = messageBody;
        }
    }
}
