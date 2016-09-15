package cn.j1angvei.cbnews.exception.temp;

/**
 * Created by Wayne on 2016/9/15.
 */

public class GetNewsFailException extends Throwable {
    public GetNewsFailException() {
    }

    public GetNewsFailException(String message) {
        super(message);
    }

    public GetNewsFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetNewsFailException(Throwable cause) {
        super(cause);
    }
}
