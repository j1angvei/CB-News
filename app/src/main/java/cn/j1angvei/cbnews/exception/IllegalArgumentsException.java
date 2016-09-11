package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/9/11.
 */

public class IllegalArgumentsException extends Exception {
    public IllegalArgumentsException() {
    }

    public IllegalArgumentsException(String message) {
        super(message);
    }

    public IllegalArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgumentsException(Throwable cause) {
        super(cause);
    }
}
