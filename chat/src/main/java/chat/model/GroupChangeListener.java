package chat.model;

/**
 * Created by affo on 09/03/18.
 */
public interface GroupChangeListener {
    void onJoin(User u);

    void onLeave(User u);
}
