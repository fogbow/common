package cloud.fogbow.common.plugins.authorization;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.UnavailableProviderException;
import cloud.fogbow.common.util.connectivity.HttpRequestClient;
import cloud.fogbow.common.util.connectivity.HttpResponse;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpRequestClient.class, RemoteAuthorizationResponse.class})
public class RemoteAuthorizationClientTest {

	private String serverAddress = "http://localhost";
	private String serverPort = "5000";
	private String authorizedEndpoint = "authorized";
	private Boolean authorized = true;
	private String finalEndpoint;
	private HashMap<String, String> headers;
	private HashMap<String, String> body;
	private RemoteAuthorizationParameters params;

	// test case: When calling the method doAuthorizationRequest, the method 
	// must set up a request correctly, call the HttpRequestClient and 
	// get the correct authorization value from the response.
	@Test
	public void testDoAuthorizationRequest() throws URISyntaxException, FogbowException {
		setUpRequest();
		setUpResponse();
		setUpParams();
		
		RemoteAuthorizationClient client = new RemoteAuthorizationClient(serverAddress, serverPort, authorizedEndpoint);

		// Verify return value is correct
		assertEquals(authorized, client.doAuthorizationRequest(params));
		
		// Verify request client was called correctly
		PowerMockito.verifyStatic(HttpRequestClient.class);
		HttpRequestClient.doGenericRequest(HttpMethod.POST, finalEndpoint, headers, body);
	}
	
	// test case: When calling the method doAuthorizationRequest and the 
	// response contains an error return code, it must throw an
	// UnavailableProviderException.
	@Test(expected = UnavailableProviderException.class)
	public void testDoAuthorizationRequestErrorCode() throws URISyntaxException, FogbowException {
		setUpRequest();
		setUpResponseErrorCode();
		setUpParams();
		
		RemoteAuthorizationClient client = new RemoteAuthorizationClient(serverAddress, serverPort, authorizedEndpoint);

		client.doAuthorizationRequest(params);
	}
	
	private void setUpRequest() {
		finalEndpoint = String.format("%s:%s/%s", serverAddress, serverPort, authorizedEndpoint);
		
		headers = new HashMap<String, String>();
	    headers.put(RemoteAuthorizationClient.CONTENT_TYPE_KEY, RemoteAuthorizationClient.AUTHORIZATION_REQUEST_CONTENT_TYPE);

	    body = new HashMap<String, String>();
		body.put("key1", "value1");
	}	

	private void setUpResponse() throws FogbowException {
		HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
		Mockito.when(httpResponse.getHttpCode()).thenReturn(HttpStatus.SC_OK);
		
		RemoteAuthorizationResponse response = Mockito.mock(RemoteAuthorizationResponse.class);
		Mockito.when(response.getAuthorized()).thenReturn(authorized);
		
		PowerMockito.mockStatic(RemoteAuthorizationResponse.class);
		BDDMockito.given(RemoteAuthorizationResponse.getRemoteAuthorizationResponse(httpResponse)).willReturn(response);
		
		PowerMockito.mockStatic(HttpRequestClient.class);
		BDDMockito.given(HttpRequestClient.doGenericRequest(HttpMethod.POST, finalEndpoint, headers, body)).willReturn(httpResponse);
	}
	
	private void setUpResponseErrorCode() throws FogbowException {
		HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
		Mockito.when(httpResponse.getHttpCode()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
		
		PowerMockito.mockStatic(HttpRequestClient.class);
		BDDMockito.given(HttpRequestClient.doGenericRequest(HttpMethod.POST, finalEndpoint, headers, body)).willReturn(httpResponse);
	}
	
	private void setUpParams() {
		params = Mockito.mock(RemoteAuthorizationParameters.class);
		Mockito.when(params.asRequestBody()).thenReturn(body);
	}
}
