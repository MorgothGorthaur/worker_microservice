package executor.service.execution.exception.step;


public class SleepException extends StepExecutionException {
    public SleepException(Throwable cause) {
        super("Failed to perform 'Sleep' step.", cause);
    }
}
