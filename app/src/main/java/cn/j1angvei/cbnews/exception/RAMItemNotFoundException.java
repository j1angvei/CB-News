package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/9/6.
 */

public class RAMItemNotFoundException extends Exception {
    public RAMItemNotFoundException() {
    }

    public RAMItemNotFoundException(String message) {
        super(message);
    }

    public RAMItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RAMItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
