package cn.j1angvei.cbnews.exception;

/**
 * Created by Wayne on 2016/9/19.
 */

public class OfflineDownloadException extends Throwable {
    public OfflineDownloadException() {
    }

    public OfflineDownloadException(String message) {
        super(message);
    }

    public OfflineDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public OfflineDownloadException(Throwable cause) {
        super(cause);
    }
}
