package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/8/25.
 */

public class MyTopicEmptyException extends Exception {
    public MyTopicEmptyException() {
    }

    public MyTopicEmptyException(String message) {
        super(message);
    }

    public MyTopicEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyTopicEmptyException(Throwable cause) {
        super(cause);
    }
}
