package socket.chat.network.commands;

/**
 * Created by affo on 08/03/18.
 */
public class TextResponse implements Response {
    public final String content;
    public final StatusCode status;

    public TextResponse(String content, StatusCode status) {
        this.content = content;
        this.status = status;
    }

    public TextResponse(String content) {
        this.content = content;
        this.status = StatusCode.OK;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }

    @Override
    public String toString() {
        return ">>> " + content;
    }
}
