package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/9/12.
 */

public class ItemNotFoundException extends Exception {
    public ItemNotFoundException() {
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
