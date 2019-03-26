package cloud.fogbow.common.exceptions;

import cloud.fogbow.common.constants.Messages;

public class DependencyDetectedException extends FogbowException {
    private static final long serialVersionUID = 1L;

    public DependencyDetectedException() {
        super(Messages.Exception.INVALID_PARAMETER);
    }

    public DependencyDetectedException(String message) {
        super(message);
    }

    public DependencyDetectedException(String message, Throwable cause) {
        super(message, cause);
    }

}
