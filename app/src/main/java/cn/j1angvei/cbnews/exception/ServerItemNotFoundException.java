package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/8/24.
 */

public class ServerItemNotFoundException extends Exception {
    public ServerItemNotFoundException() {
    }

    public ServerItemNotFoundException(String message) {
        super(message);
    }

    public ServerItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerItemNotFoundException(Throwable cause) {
        super(cause);
    }

}
