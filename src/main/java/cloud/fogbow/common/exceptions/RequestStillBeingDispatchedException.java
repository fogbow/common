package cloud.fogbow.common.exceptions;

import cloud.fogbow.common.constants.Messages;

public class RequestStillBeingDispatchedException extends FogbowException {
    private static final long serialVersionUID = 1L;

    public RequestStillBeingDispatchedException(String message) {
        super(message);
    }

    public RequestStillBeingDispatchedException(String message, Throwable cause) {
        super(message, cause);
    }

}
