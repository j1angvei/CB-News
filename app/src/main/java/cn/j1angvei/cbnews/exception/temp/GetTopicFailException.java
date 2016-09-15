package cn.j1angvei.cbnews.exception.temp;

/**
 * Created by Wayne on 2016/9/15.
 */

public class GetTopicFailException extends Throwable {
    public GetTopicFailException() {
    }

    public GetTopicFailException(String message) {
        super(message);
    }

    public GetTopicFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetTopicFailException(Throwable cause) {
        super(cause);
    }
}
