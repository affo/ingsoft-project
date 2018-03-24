package socket.chat.exceptions;

public class NonExistentGroupException extends IllegalArgumentException {
    public NonExistentGroupException(String s) {
        super(s);
    }
}