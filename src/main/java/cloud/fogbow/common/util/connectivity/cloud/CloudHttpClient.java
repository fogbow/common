package cloud.fogbow.common.util.connectivity.cloud;

import cloud.fogbow.common.constants.CloudStackConstants;
import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InternalServerErrorException;
import cloud.fogbow.common.models.CloudUser;
import cloud.fogbow.common.util.GsonHolder;
import cloud.fogbow.common.util.connectivity.HttpErrorConditionToFogbowExceptionMapper;
import cloud.fogbow.common.util.connectivity.HttpRequest;
import cloud.fogbow.common.util.connectivity.HttpRequestClient;
import cloud.fogbow.common.util.connectivity.HttpResponse;

import org.apache.http.HttpStatus;

import com.google.common.annotations.VisibleForTesting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CloudHttpClient<T extends CloudUser> {

    public static final String EMPTY_BODY = "{}";
    public static final int SC_REQUEST_HEADER_FIELDS_TOO_LARGE = 431;

    public CloudHttpClient() {}

    public String doGetRequest(String url, T cloudUser) throws FogbowException {
        return callDoGenericRequest(HttpMethod.GET, url, EMPTY_BODY, cloudUser);
    }

    public void doDeleteRequest(String url, T cloudUser) throws FogbowException {
        callDoGenericRequest(HttpMethod.DELETE, url, EMPTY_BODY, cloudUser);
    }

    public String doPostRequest(String url, String bodyContent, T cloudUser) throws FogbowException {
        return callDoGenericRequest(HttpMethod.POST, url, bodyContent, cloudUser);
    }

    @VisibleForTesting
    String callDoGenericRequest(HttpMethod method, String url, String bodyContent, T cloudUser) throws FogbowException {

        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> body = GsonHolder.getInstance().fromJson(bodyContent, HashMap.class);
        HttpResponse response = doGenericRequest(method, url, headers, body, cloudUser);
        if (response.getHttpCode() > HttpStatus.SC_NO_CONTENT) {
            if (response.getHttpCode() == SC_REQUEST_HEADER_FIELDS_TOO_LARGE) {
                // When the status code is 431, the error message must be obtained in the
                // response headers.
                Map<String, List<String>> responseHeaders = response.getHeaders();
                throw HttpErrorConditionToFogbowExceptionMapper.map(response.getHttpCode(), getMessageFrom(responseHeaders));
            } else {
                throw HttpErrorConditionToFogbowExceptionMapper.map(response.getHttpCode(), response.getContent());
            }
        }
        return response.getContent();
    }

    @VisibleForTesting
    String getMessageFrom(Map<String, List<String>> responseHeaders) {
        String message = null;
        if (responseHeaders.containsKey(CloudStackConstants.X_DESCRIPTION_KEY)) {
            List<String> descriptions = responseHeaders.get(CloudStackConstants.X_DESCRIPTION_KEY);
            message = descriptions.listIterator().next();
        }
        return message;
    }

    public HttpResponse doGenericRequest(HttpMethod method, String url, Map<String, String> headers,
            Map<String, String> body, T cloudUser) throws FogbowException {

        HttpRequest request = createHttpRequest(method, url, body, headers);
        HttpRequest preparedRequest = prepareRequest(request, cloudUser);

        HttpMethod requestMethod = preparedRequest.getMethod();
        String requestUrl = preparedRequest.getUrl();
        Map<String, String> requestHeaders = preparedRequest.getHeaders();
        Map<String, String> requestBody = preparedRequest.getBody();
        return HttpRequestClient.doGenericRequest(requestMethod, requestUrl, requestHeaders, requestBody);
    }

    @VisibleForTesting
    HttpRequest createHttpRequest(HttpMethod method, String url, Map<String, String> body, Map<String, String> headers)
            throws InternalServerErrorException {
        return new HttpRequest(method, url, body, headers);
    }

    public abstract HttpRequest prepareRequest(HttpRequest genericRequest, T cloudUser) throws FogbowException;

}
