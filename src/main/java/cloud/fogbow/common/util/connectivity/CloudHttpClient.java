package cloud.fogbow.common.util.connectivity;

import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.models.CloudUser;
import cloud.fogbow.common.util.GsonHolder;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import java.util.HashMap;

public abstract class CloudHttpClient {
    public static final String EMPTY_BODY = "{}";

    public CloudHttpClient() {
    }

    public String doGetRequest(String url, CloudUser token) throws FogbowException, HttpResponseException {
        return callDoGenericRequest(HttpMethod.GET, url, EMPTY_BODY, token);
    }

    public void doDeleteRequest(String url, CloudUser token) throws FogbowException, HttpResponseException {
        callDoGenericRequest(HttpMethod.DELETE, url, EMPTY_BODY, token);
    }

    public String doPostRequest(String url, String bodyContent, CloudUser token)
            throws FogbowException, HttpResponseException {
        return callDoGenericRequest(HttpMethod.POST, url, bodyContent, token);
    }

    private String callDoGenericRequest(HttpMethod method, String url, String bodyContent, CloudUser token) throws FogbowException, HttpResponseException {
        HashMap<String, String> body = GsonHolder.getInstance().fromJson(bodyContent, HashMap.class);

        HashMap<String, String> headers = new HashMap<>();
        HttpResponse response = doGenericRequest(method, url, headers, body, token);

        if (response.getHttpCode() > HttpStatus.SC_NO_CONTENT) {
            throw new HttpResponseException(response.getHttpCode(), response.getContent());
        }

        return response.getContent();
    }

    public HttpResponse doGenericRequest(HttpMethod method, String url, HashMap<String, String> headers,
                                         HashMap<String, String> body , CloudUser token) throws FogbowException {
        GenericRequest request = new GenericRequest(method, url, body, headers);
        GenericRequest preparedRequest = prepareRequest(request, token);

        return HttpRequestClientUtil.doGenericRequest(preparedRequest.getMethod(),
                preparedRequest.getUrl(), preparedRequest.getHeaders(), preparedRequest.getBody());
    }

    public abstract GenericRequest prepareRequest(GenericRequest genericRequest, CloudUser token);
}
