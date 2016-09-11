package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/8/24.
 */

public class WEBItemNotFoundException extends Exception {
    public WEBItemNotFoundException() {
    }

    public WEBItemNotFoundException(String message) {
        super(message);
    }

    public WEBItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WEBItemNotFoundException(Throwable cause) {
        super(cause);
    }

}
