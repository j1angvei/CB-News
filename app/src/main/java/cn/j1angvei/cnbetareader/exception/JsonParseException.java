package cn.j1angvei.cnbetareader.exception;

/**
 * Created by Wayne on 2016/9/1.
 */

public class JsonParseException extends Exception {
    public JsonParseException() {
    }

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonParseException(Throwable cause) {
        super(cause);
    }
}
