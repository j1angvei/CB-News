package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/9/13.
 */

public class ConvertFailException extends Throwable {
    public ConvertFailException() {
    }

    public ConvertFailException(String message) {
        super(message);
    }

    public ConvertFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertFailException(Throwable cause) {
        super(cause);
    }
}
