package rmi.rmitter.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by affo on 13/03/18.
 *
 * Not a remote object
 */
public class Hashtag implements Serializable {
    private final String tag;
    private final List<FeedObserver> observers = new LinkedList<>();

    public Hashtag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "#" + tag;
    }

    public void observe(FeedObserver follower) {
        observers.add(follower);
    }

    public void usedIn(Post post) throws RemoteException {
        for (FeedObserver observer : observers) {
            observer.onHashtagUse(this, post);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hashtag hashtag = (Hashtag) o;

        return tag != null ? tag.equals(hashtag.tag) : hashtag.tag == null;
    }

    @Override
    public int hashCode() {
        return tag != null ? tag.hashCode() : 0;
    }
}
