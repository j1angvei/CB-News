package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/8/24.
 */

public class SQLItemNotFoundException extends Exception {
    public SQLItemNotFoundException() {
    }

    public SQLItemNotFoundException(String message) {
        super(message);
    }

    public SQLItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
