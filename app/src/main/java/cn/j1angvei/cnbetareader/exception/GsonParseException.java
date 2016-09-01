package cn.j1angvei.cnbetareader.exception;

/**
 * Created by Wayne on 2016/9/1.
 */

public class GsonParseException extends Exception {
    public GsonParseException() {
    }

    public GsonParseException(String message) {
        super(message);
    }

    public GsonParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public GsonParseException(Throwable cause) {
        super(cause);
    }
}
