package cloud.fogbow.common.util.connectivity.cloud.openstack;

import cloud.fogbow.common.constants.HttpConstants;
import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.constants.OpenStackConstants;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.OpenStackV3User;
import cloud.fogbow.common.util.connectivity.cloud.CloudHttpClient;
import cloud.fogbow.common.util.connectivity.HttpRequest;

import java.util.Map;

public class OpenStackHttpClient extends CloudHttpClient<OpenStackV3User> {

    public OpenStackHttpClient() {
    }

    @Override
    public HttpRequest prepareRequest(HttpRequest genericRequest, OpenStackV3User cloudUser) throws UnexpectedException {
        HttpRequest clonedRequest = new HttpRequest(
            genericRequest.getMethod(), genericRequest.getUrl(), genericRequest.getBody(), genericRequest.getHeaders());

        Map<String, String> headers = clonedRequest.getHeaders();

        headers.put(OpenStackConstants.X_AUTH_TOKEN_KEY, cloudUser.getToken());

        if (genericRequest.getMethod().equals(HttpMethod.GET)
                || genericRequest.getMethod().equals(HttpMethod.POST)) {
            headers.put(HttpConstants.CONTENT_TYPE_KEY, HttpConstants.JSON_CONTENT_TYPE_KEY);
            headers.put(HttpConstants.ACCEPT_KEY, HttpConstants.JSON_CONTENT_TYPE_KEY);
        }
        return clonedRequest;
    }
}
