package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/9/17.
 */

public class NoNetworkException extends Throwable {
    public NoNetworkException() {
    }

    public NoNetworkException(String message) {
        super(message);
    }

    public NoNetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoNetworkException(Throwable cause) {
        super(cause);
    }
}
