package cloud.fogbow.common.util.connectivity.cloud.cloudstack;

import cloud.fogbow.common.exceptions.*;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

public class CloudStackHttpToFogbowExceptionMapper {

	@Deprecated
    public static void map(HttpResponseException e) throws FogbowException {
        switch (e.getStatusCode()) {
            case HttpStatus.SC_FORBIDDEN:
                throw new UnauthorizedRequestException(e.getMessage(), e);
            case HttpStatus.SC_UNAUTHORIZED:
                throw new UnauthenticatedUserException(e.getMessage(), e);
            case HttpStatus.SC_NOT_FOUND:
                throw new InstanceNotFoundException(e.getMessage(), e);
            default:
                throw new UnexpectedException(e.getMessage(), e);
        }
    }
    
    public static FogbowException get(HttpResponseException e) {
        switch (e.getStatusCode()) {
            case HttpStatus.SC_FORBIDDEN:
                return new UnauthorizedRequestException(e.getMessage(), e);
            case HttpStatus.SC_UNAUTHORIZED:
                return new UnauthenticatedUserException(e.getMessage(), e);
            case HttpStatus.SC_NOT_FOUND:
                return new InstanceNotFoundException(e.getMessage(), e);
            default:
                return new UnexpectedException(e.getMessage(), e);
        }
    }    
}
