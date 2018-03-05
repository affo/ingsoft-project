package chat.model;

/**
 * Created by affo on 05/03/18.
 */
public interface MessageReceivedObserver {
    void onMessage(String groupName, String messageBody);
}
