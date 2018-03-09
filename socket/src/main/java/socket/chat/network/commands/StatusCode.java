package socket.chat.network.commands;

/**
 * Created by affo on 08/03/18.
 */
public enum StatusCode {
    OK(200),
    KO(400);

    StatusCode(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }
}
