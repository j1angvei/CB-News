package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/9/1.
 */

public class BeanConvertException extends Exception {
    public BeanConvertException() {
    }

    public BeanConvertException(String message) {
        super(message);
    }

    public BeanConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanConvertException(Throwable cause) {
        super(cause);
    }
}
