package chat.exceptions;

import chat.model.Group;
import chat.model.User;

/**
 * Created by affo on 05/03/18.
 */
public class UserNotInGroupException extends RuntimeException {
    private final User user;
    private final Group group;

    public UserNotInGroupException(User user, Group group) {
        this.user = user;
        this.group = group;
    }

    @Override
    public String getMessage() {
        String message = "User not in group: ";
        return message + user.getUsername() + " <> " + group.getName();
    }
}
