package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/8/24.
 */

public class CsrfTokenParseException extends Exception {
    public CsrfTokenParseException() {
    }

    public CsrfTokenParseException(String message) {
        super(message);
    }

    public CsrfTokenParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsrfTokenParseException(Throwable cause) {
        super(cause);
    }
}
