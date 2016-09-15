package cn.j1angvei.cbnews.exception.temp;

/**
 * Created by Wayne on 2016/9/15.
 */

public class GetCommentFailException extends Throwable {
    public GetCommentFailException() {
    }

    public GetCommentFailException(String message) {
        super(message);
    }

    public GetCommentFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetCommentFailException(Throwable cause) {
        super(cause);
    }
}
