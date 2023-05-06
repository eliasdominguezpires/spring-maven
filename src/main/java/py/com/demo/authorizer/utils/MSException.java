package py.com.demo.authorizer.utils;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MSException extends Exception {

    private int status;
    private String debugMsg;

    public MSException(String message, int status) {
        super(message);
        this.status = status;
    }

    public MSException(String message, int status, String msg) {
        this(message, status);

        this.debugMsg = msg;
    }
}
