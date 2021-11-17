package cloud.fogbow.common.plugins.authorization;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.annotations.VisibleForTesting;

import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.UnavailableProviderException;
import cloud.fogbow.common.util.connectivity.HttpRequestClient;
import cloud.fogbow.common.util.connectivity.HttpResponse;

public class RemoteAuthorizationClient {
    @VisibleForTesting
	static final String CONTENT_TYPE_KEY = "Content-Type";
    @VisibleForTesting
    static final String AUTHORIZATION_REQUEST_CONTENT_TYPE = "application/json";
    
    private String authorizationUrl;

    public RemoteAuthorizationClient(String serverAddress, String serverPort, String authorizedEndpoint) throws URISyntaxException {
    	this.authorizationUrl = getAuthorizationEndpoint(serverAddress, serverPort, authorizedEndpoint);
    }
    
    private String getAuthorizationEndpoint(String address, String port, String path) throws URISyntaxException {
		URI uri = new URI(address);
		uri = UriComponentsBuilder.fromUri(uri).port(port).path(path).build(true).toUri();
		return uri.toString();
	}
    
    public boolean doAuthorizationRequest(RemoteAuthorizationParameters params) throws URISyntaxException, FogbowException {
        HttpResponse response = doRequest(authorizationUrl, params);

        if (response.getHttpCode() > HttpStatus.SC_OK) {
            Throwable e = new HttpResponseException(response.getHttpCode(), response.getContent());
            throw new UnavailableProviderException(e.getMessage());
        }
        
        return RemoteAuthorizationResponse.getRemoteAuthorizationResponse(response).getAuthorized();
    }

    private HttpResponse doRequest(String endpoint, RemoteAuthorizationParameters params)
	        throws URISyntaxException, FogbowException {
	    // header
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put(CONTENT_TYPE_KEY, AUTHORIZATION_REQUEST_CONTENT_TYPE);
	    
	    return HttpRequestClient.doGenericRequest(HttpMethod.POST, endpoint, headers, params.asRequestBody());
	}
}
