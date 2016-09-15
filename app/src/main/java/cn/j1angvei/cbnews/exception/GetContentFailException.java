package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/9/15.
 */

public class GetContentFailException extends Throwable {
    public GetContentFailException() {
    }

    public GetContentFailException(String message) {
        super(message);
    }

    public GetContentFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetContentFailException(Throwable cause) {
        super(cause);
    }
}
