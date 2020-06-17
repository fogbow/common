package cloud.fogbow.common.exceptions;

import cloud.fogbow.common.constants.Messages;

public class ConfigurationErrorException extends FogbowException {
    private static final long serialVersionUID = 1L;

    public ConfigurationErrorException() {
        super(Messages.Exception.CONFIGURATION_ERROR);
    }

    public ConfigurationErrorException(Throwable cause) {
        super(Messages.Exception.CONFIGURATION_ERROR, cause);
    }

    public ConfigurationErrorException(String message) {
        super(message);
    }

    public ConfigurationErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}

