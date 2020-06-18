package cloud.fogbow.common.exceptions;

import cloud.fogbow.common.constants.Messages;

public class FogbowException extends Exception {
    private static final long serialVersionUID = 1L;

    public FogbowException(String message) {
        super(message);
    }
}
