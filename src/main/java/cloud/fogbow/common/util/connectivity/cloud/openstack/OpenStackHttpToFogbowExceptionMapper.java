package cloud.fogbow.common.util.connectivity.cloud.openstack;

import cloud.fogbow.common.exceptions.*;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

public class OpenStackHttpToFogbowExceptionMapper {

    public static void map(HttpResponseException e)
            throws FogbowException {
        switch (e.getStatusCode()) {
            case HttpStatus.SC_FORBIDDEN:
                throw new UnauthorizedRequestException(e.getMessage(), e);
            case HttpStatus.SC_UNAUTHORIZED:
                throw new UnauthenticatedUserException(e.getMessage(), e);
            case HttpStatus.SC_BAD_REQUEST:
                throw new InvalidParameterException(e.getMessage(), e);
            case HttpStatus.SC_NOT_FOUND:
                throw new InstanceNotFoundException(e.getMessage(), e);
            case HttpStatus.SC_CONFLICT:
                throw new ConfigurationErrorException(e.getMessage(), e);
            case HttpStatus.SC_NOT_ACCEPTABLE:
                throw new UnacceptableOperationException(e.getMessage(), e);
            case HttpStatus.SC_GATEWAY_TIMEOUT:
                throw new UnavailableProviderException(e.getMessage(), e);
            default:
                throw new UnexpectedException(e.getMessage(), e);
        }
    }
}
