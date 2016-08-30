package cn.j1angvei.cnbetareader.exception;

/**
 * Created by Wayne on 2016/8/31.
 */

public class NoCommentException extends Exception {
    public NoCommentException() {
    }

    public NoCommentException(String message) {
        super(message);
    }

    public NoCommentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCommentException(Throwable cause) {
        super(cause);
    }

}
