package cloud.fogbow.common.util.connectivity.cloud.cloudstack;

import cloud.fogbow.common.exceptions.InternalServerErrorException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import org.apache.http.client.utils.URIBuilder;

public abstract class CloudStackRequest {
    private URIBuilder uriBuilder;

    protected CloudStackRequest() {}

    protected CloudStackRequest(String baseEndpoint) throws InternalServerErrorException {
        this.uriBuilder = CloudStackUrlUtil.createURIBuilder(baseEndpoint, getCommand());
    }

    protected void addParameter(String parameter, String value) {
        if (value != null) {
            this.uriBuilder.addParameter(parameter, value);
        }
    }

    public URIBuilder getUriBuilder() {
        return this.uriBuilder;
    }

    public abstract String getCommand();
}
