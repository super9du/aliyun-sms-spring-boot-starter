package super9du.sia.aliyun.sms;

public class AliyunSmsStarterException extends RuntimeException {
    public AliyunSmsStarterException() {
        super();
    }

    public AliyunSmsStarterException(String message) {
        super(message);
    }

    public AliyunSmsStarterException(String message, Throwable cause) {
        super(message, cause);
    }

    public AliyunSmsStarterException(Throwable cause) {
        super(cause);
    }

    public AliyunSmsStarterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
