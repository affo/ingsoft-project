package rmi.rmitter.control;

import rmi.rmitter.model.*;
import rmi.rmitter.view.RemoteBaseView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by affo on 19/03/18.
 */
public class Controller extends UnicastRemoteObject implements RemoteController {
    private final Map<String, RemoteBaseView> views = new HashMap<>();

    // model
    private transient final Database database;

    public Controller() throws RemoteException {
        super();
        database = Database.get();
    }

    private User getLoggedUser(String token) throws RemoteException {
        try {
            return database.getLoggedUser(token);
        } catch (IllegalArgumentException iae) {
            throw new RemoteException("Forbidden, invalid token");
        }
    }

    @Override
    public synchronized User me(String token) throws RemoteException {
        return getLoggedUser(token);
    }

    @Override
    public synchronized void followUser(String token, String username) throws RemoteException {
        User loggedUser = getLoggedUser(token);
        User followee = database.getUser(username);

        if (followee == null) {
            throw new RemoteException("Invalid username " + username);
        }

        followee.follow(loggedUser);
        views.get(token).ack("Now following @" + username);
    }

    @Override
    public synchronized String login(String username, RemoteBaseView view) throws RemoteException {
        String token = database.login(username);
        views.put(token, view);
        view.ack("Logging in as @" + username);

        return token;
    }

    @Override
    public synchronized void observeUser(String token, String username,
                                         UserObserver userObserver, FeedObserver feedObserver) throws RemoteException {
        User loggedUser = getLoggedUser(token);
        if (!loggedUser.getUsername().equals(username)) {
            throw new RemoteException("Not authorized to observe " + username);
        }

        loggedUser.observeUser(userObserver);
        loggedUser.observePosts(feedObserver);
    }

    @Override
    public synchronized void logout(String token) throws RemoteException {
        database.logout(token);
    }

    @Override
    public synchronized Post post(String token, String content) throws RemoteException {
        User loggedUser = getLoggedUser(token);
        return loggedUser.post(content);
    }

    @Override
    public synchronized void followHashtag(String token, String hashtag, FeedObserver observer) throws RemoteException {
        getLoggedUser(token);

        database.getOrCreateHashtag(hashtag).observe(observer);
        views.get(token).ack("Now following #" + hashtag);
    }
}
