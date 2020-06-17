package cloud.fogbow.common.exceptions;

import cloud.fogbow.common.constants.Messages;

public class UnavailableProviderException extends FogbowException {
    private static final long serialVersionUID = 1L;

    public UnavailableProviderException() {
        super(Messages.Exception.UNAVAILABLE_PROVIDER);
    }

    public UnavailableProviderException(Throwable cause) {
        super(Messages.Exception.UNAVAILABLE_PROVIDER, cause);
    }

    public UnavailableProviderException(String message) {
        super(message);
    }

    public UnavailableProviderException(String message, Throwable cause) {
        super(message, cause);
    }

}
