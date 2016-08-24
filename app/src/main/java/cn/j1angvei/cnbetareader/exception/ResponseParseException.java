package cn.j1angvei.cnbetareader.exception;

/**
 * Created by Wayne on 2016/8/24.
 */

public class ResponseParseException extends Exception {
    public ResponseParseException() {
    }

    public ResponseParseException(String message) {
        super(message);
    }

    public ResponseParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseParseException(Throwable cause) {
        super(cause);
    }
}
