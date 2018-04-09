package rmi.rmitter.model;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by affo on 20/03/18.
 *
 * Singleton, keeps the entire state of the application.
 */
public class Database {
    private static Database instance;

    private Database() {

    }

    public synchronized static Database get() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    // implementation
    private final Map<String, User> usersByName = new HashMap<>();
    private final Map<String, User> usersByToken = new HashMap<>();
    private final Map<String, Hashtag> hashtags = new HashMap<>();

    public synchronized User getLoggedUser(String token) throws IllegalArgumentException {
        User logged = usersByToken.get(token);
        if (logged == null) {
            throw new IllegalArgumentException("Forbidden, invalid token");
        }

        return logged;
    }

    public synchronized User getUser(String username) {
        return usersByName.get(username);
    }

    public synchronized Hashtag getOrCreateHashtag(String ht) {
        return hashtags.computeIfAbsent(ht, Hashtag::new);
    }

    public synchronized String login(String username) throws RemoteException {
        if (usersByToken.values().stream().map(User::getUsername).anyMatch(u -> u.equals(username))) {
            throw new RemoteException("The user is already logged: " + username);
        }

        String token = UUID.randomUUID().toString();

        User user = usersByName.get(username);
        if (user == null) {
            user = new User(username);
            usersByName.put(username, user);
        }
        usersByToken.put(token, user);

        return token;
    }

    public synchronized void logout(String token) {
        usersByToken.remove(token);
    }
}
