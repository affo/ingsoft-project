package rmi.rmitter.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by affo on 13/03/18.
 *
 * Observes the updated of state of a User
 */
public interface UserObserver extends Remote {
    void onNewPost(Post post) throws RemoteException;
    void onMention(Post post) throws RemoteException;
    void onFollower(User follower) throws RemoteException;
}
