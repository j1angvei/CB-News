package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/8/24.
 */

public class LocalItemNotFoundException extends Exception {
    public LocalItemNotFoundException() {
    }

    public LocalItemNotFoundException(String message) {
        super(message);
    }

    public LocalItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocalItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
