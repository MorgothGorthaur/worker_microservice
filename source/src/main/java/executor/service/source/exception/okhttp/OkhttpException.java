package executor.service.source.exception.okhttp;


import executor.service.source.exception.SourceException;

public class OkhttpException extends SourceException {
    public OkhttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public OkhttpException(String message) {
        super(message);
    }
}
