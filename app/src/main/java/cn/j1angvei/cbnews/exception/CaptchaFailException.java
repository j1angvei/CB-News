package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/9/13.
 */

public class CaptchaFailException extends Throwable {
    public CaptchaFailException() {
    }

    public CaptchaFailException(String message) {
        super(message);
    }

    public CaptchaFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaFailException(Throwable cause) {
        super(cause);
    }
}
