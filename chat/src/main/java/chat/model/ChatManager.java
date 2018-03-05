package chat.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by affo on 27/02/18.
 */
public class ChatManager {
    private Set<Group> groups = new HashSet<>();

    public ChatManager() {
    }

    public Set<Group> groups() {
        return groups;
    }

    public Group createGroup() {
        Group group = new Group();
        groups.add(group);
        return group;
    }
}
