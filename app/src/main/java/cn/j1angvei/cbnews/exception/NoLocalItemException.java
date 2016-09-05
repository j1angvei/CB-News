package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/8/24.
 */

public class NoLocalItemException extends Exception {
    public NoLocalItemException() {
    }

    public NoLocalItemException(String message) {
        super(message);
    }

    public NoLocalItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoLocalItemException(Throwable cause) {
        super(cause);
    }
}
