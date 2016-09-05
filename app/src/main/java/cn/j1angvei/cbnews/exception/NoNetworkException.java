package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/8/24.
 */

public class NoNetworkException extends Exception {
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