package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/9/6.
 */

public class NoItemInRamException extends Exception {
    public NoItemInRamException() {
    }

    public NoItemInRamException(String message) {
        super(message);
    }

    public NoItemInRamException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoItemInRamException(Throwable cause) {
        super(cause);
    }
}
