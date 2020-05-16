package cloud.fogbow.common.exceptions;

public class OnGoingOperationException extends FogbowException {
    private static final long serialVersionUID = 1L;

    public OnGoingOperationException(String message) {
        super(message);
    }

    public OnGoingOperationException(String message, Throwable cause) {
        super(message, cause);
    }

}
