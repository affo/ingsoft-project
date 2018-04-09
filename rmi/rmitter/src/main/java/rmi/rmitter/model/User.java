package rmi.rmitter.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by affo on 13/03/18.
 */
public class User implements Serializable {
    private final String username;
    private final List<User> followers;
    private int numberOfPosts;

    private transient final List<UserObserver> userObservers;
    private transient final List<FeedObserver> feedObservers;


    public User(String username) {
        super();
        this.username = username;
        this.followers = new LinkedList<>();

        this.userObservers = new LinkedList<>();
        this.feedObservers = new LinkedList<>();
    }

    public void observeUser(UserObserver observer) {
        userObservers.add(observer);
    }

    public void observePosts(FeedObserver observer) {
        feedObservers.add(observer);
    }

    public String getUsername() {
        return username;
    }

    public int getPostCount() {
        return numberOfPosts;
    }

    public int getNumberOfFollowers() {
        return followers.size();
    }

    public void follow(User user) throws RemoteException {
        followers.add(user);

        // -------- notification
        for (UserObserver observer : userObservers) {
            observer.onFollower(user);
        }
    }

    public void mention(Post post) throws RemoteException {
        // -------- notification
        for (UserObserver observer : userObservers) {
            observer.onMention(post);
        }
    }

    public void followeePosted(Post post) throws RemoteException {
        // -------- notification
        for (FeedObserver observer : feedObservers) {
            observer.onFolloweePost(post);
        }
    }


    public Post post(String content) throws RemoteException {
        Post post = new Post(this, content);
        numberOfPosts++;

        for (User tagged : post.getTaggedUsers()) {
            tagged.mention(post);
        }

        for (Hashtag hashtag : post.getHashtags()) {
            hashtag.usedIn(post);
        }

        for (User user : followers) {
            user.followeePosted(post);
        }

        // -------- notification
        for (UserObserver observer : userObservers) {
            observer.onNewPost(post);
        }

        return post;
    }
}
