package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/9/1.
 */

public class ConvertException extends Exception {
    public ConvertException() {
    }

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertException(Throwable cause) {
        super(cause);
    }
}
