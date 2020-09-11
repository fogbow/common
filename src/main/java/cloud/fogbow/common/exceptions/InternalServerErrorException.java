package cloud.fogbow.common.exceptions;

import cloud.fogbow.common.constants.Messages;

public class InternalServerErrorException extends FogbowException {
    private static final long serialVersionUID = 1L;

    public InternalServerErrorException() {
        super(Messages.Exception.UNEXPECTED);
    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}
