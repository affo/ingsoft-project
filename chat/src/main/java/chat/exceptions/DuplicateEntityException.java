package chat.exceptions;

/**
 * Created by affo on 27/02/18.
 */
public class DuplicateEntityException extends IllegalArgumentException {
    public DuplicateEntityException(String s) {
        super(s);
    }
}
