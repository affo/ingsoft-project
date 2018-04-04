package rmi.rmitter.control;

import rmi.rmitter.model.*;
import rmi.rmitter.view.RemoteBaseView;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by affo on 20/03/18.
 *
 * Remote object that exports the endpoints for interacting with the application.
 * It uses a String token in order to track and authenticate users.
 */
public interface RemoteController extends Remote {
    User me(String token) throws RemoteException;

    void followUser(String token, String username) throws RemoteException;

    String login(String username, RemoteBaseView view) throws RemoteException;

    void observeUser(String token, String username,
                     UserObserver userObserver, FeedObserver feedObserver) throws RemoteException;

    void logout(String token) throws RemoteException;

    Post post(String token, String content) throws RemoteException;

    void followHashtag(String token, String hashtag, FeedObserver observer) throws RemoteException;
}
