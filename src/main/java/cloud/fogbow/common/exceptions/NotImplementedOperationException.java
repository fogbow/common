package cloud.fogbow.common.exceptions;

import cloud.fogbow.common.constants.Messages;

public class NotImplementedOperationException extends FogbowException {
    private static final long serialVersionUID = 1L;

    public NotImplementedOperationException() {
        super(Messages.Exception.NOT_IMPLEMENTED_OPERATION);
    }
    
    public NotImplementedOperationException(String message) {
        super(message);
    }
}
