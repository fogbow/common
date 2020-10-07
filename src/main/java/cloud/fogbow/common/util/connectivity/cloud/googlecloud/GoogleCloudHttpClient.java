package cloud.fogbow.common.util.connectivity.cloud.googlecloud;

import cloud.fogbow.common.constants.GoogleCloudConstants;
import cloud.fogbow.common.constants.HttpConstants;
import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.models.GoogleCloudUser;
import cloud.fogbow.common.util.connectivity.HttpRequest;
import cloud.fogbow.common.util.connectivity.cloud.CloudHttpClient;

import java.util.Map;

public class GoogleCloudHttpClient extends CloudHttpClient<GoogleCloudUser> {
    @Override
    public HttpRequest prepareRequest(HttpRequest genericRequest, GoogleCloudUser cloudUser) throws FogbowException {
        HttpRequest clonedRequest = new HttpRequest(
                genericRequest.getMethod(), genericRequest.getUrl(), genericRequest.getBody(), genericRequest.getHeaders());

        String token = cloudUser.getToken();

        Map<String, String> headers = clonedRequest.getHeaders();
        String bearerToken = String.format(GoogleCloudConstants.BEARER_S, token);
        headers.put(GoogleCloudConstants.AUTHORIZATION_KEY, bearerToken);

        if (genericRequest.getMethod().equals(HttpMethod.GET)
                || genericRequest.getMethod().equals(HttpMethod.POST)) {
            headers.put(HttpConstants.CONTENT_TYPE_KEY, HttpConstants.JSON_CONTENT_TYPE_KEY);
            headers.put(HttpConstants.ACCEPT_KEY, HttpConstants.JSON_CONTENT_TYPE_KEY);
        }

        return clonedRequest;
    }
}
