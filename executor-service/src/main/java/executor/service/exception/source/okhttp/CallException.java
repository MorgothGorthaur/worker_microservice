package executor.service.exception.source.okhttp;

public class CallException extends OkhttpException {
    public CallException(Throwable ex) {
        super("Error executing the request", ex);
    }
}
