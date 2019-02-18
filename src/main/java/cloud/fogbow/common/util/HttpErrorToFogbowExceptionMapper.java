package cloud.fogbow.common.util;

import cloud.fogbow.common.exceptions.*;
import org.apache.http.HttpStatus;

public class HttpErrorToFogbowExceptionMapper {
    public static FogbowException map(int httpCode, String message) {
        switch(httpCode) {
            case HttpStatus.SC_FORBIDDEN:
                return new UnauthorizedRequestException(message);
            case HttpStatus.SC_UNAUTHORIZED:
                return new UnauthenticatedUserException(message);
            case HttpStatus.SC_BAD_REQUEST:
                return new InvalidParameterException(message);
            case HttpStatus.SC_NOT_FOUND:
                return new InstanceNotFoundException(message);
            case HttpStatus.SC_CONFLICT:
                return new QuotaExceededException(message);
            case HttpStatus.SC_NOT_ACCEPTABLE:
                return new NoAvailableResourcesException(message);
            case HttpStatus.SC_GATEWAY_TIMEOUT:
                return new UnavailableProviderException(message);
            case HttpStatus.SC_INTERNAL_SERVER_ERROR:
            case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE:
            default:
                return new UnexpectedException(message);
        }
    }
}
