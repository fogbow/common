package cloud.fogbow.common.exceptions;

import cloud.fogbow.common.constants.Messages;

public class RemoteCommunicationException extends FogbowException {
    private static final long serialVersionUID = 1L;

    public RemoteCommunicationException() {
        super(Messages.Exception.REMOTE_COMMUNICATION);
    }

    public RemoteCommunicationException(String message) {
        super(message);
    }

    public RemoteCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
