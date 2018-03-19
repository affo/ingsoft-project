package rmi.rmitter.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by affo on 13/03/18.
 *
 * Not a remote object
 */
public class Post implements Serializable {
    private final String content;
    private final User poster;
    private final List<User> taggedUsers;
    private final List<Hashtag> hashtags;

    public Post(User poster, String content) {
        this.poster = poster;
        this.content = content;
        this.taggedUsers = new LinkedList<>();
        this.hashtags = new LinkedList<>();

        parse();
    }

    private void parse() {
        for (String word : content.split(" ")) {
            char start = word.charAt(0);
            String cleanWord = word.substring(1, word.length());

            switch (start) {
                case '#':
                    hashtags.add(Database.get().getOrCreateHashtag(cleanWord));
                    break;
                case '@':
                    User user = Database.get().getUser(cleanWord);
                    if (user != null) {
                        taggedUsers.add(user);
                    }
                    break;
            }
        }
    }

    public String getContent() {
        return content;
    }

    public User getPoster() {
        return poster;
    }

    public List<User> getTaggedUsers() {
        return taggedUsers;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }
}
