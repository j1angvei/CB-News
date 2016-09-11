package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/9/11.
 */

public class NeedRefreshException extends Exception {
    public NeedRefreshException() {
    }

    public NeedRefreshException(String message) {
        super(message);
    }

    public NeedRefreshException(String message, Throwable cause) {
        super(message, cause);
    }

    public NeedRefreshException(Throwable cause) {
        super(cause);
    }
}
