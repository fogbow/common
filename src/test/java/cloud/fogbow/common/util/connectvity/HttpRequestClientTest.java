package cloud.fogbow.common.util.connectvity;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.util.connectivity.HttpRequestClient;

public class HttpRequestClientTest {

	private static final String ANY_VALUE = "anything";
	private static final String DEFAULT_URL = "http://localhost:8080";
	
	private HttpRequestClient client;
	
	@Before
	public void setUp() {
		this.client = Mockito.spy(new HttpRequestClient());
	}
	
	// test case: When invoking the doGenericRequest method with an invalid URL, it
	// must throw the InvalidParameterException.
	@Test(expected = InvalidParameterException.class) // verify
	public void testDoGenericRequestWithInvalidURL() throws FogbowException {
		// set up
		HttpMethod httpMethod = HttpMethod.GET;
		String invalidUrl = ANY_VALUE;
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> body = new HashMap<String, String>();

		// exercise
		this.client.doGenericRequest(httpMethod, invalidUrl, headers, body);
	}
	
	// test case: When invoking the doGenericRequest method with a valid URL, but
	// can not open a connection through this URL, it must throw the
	// UnexpectedException.
	@Test(expected = UnexpectedException.class) // verify
	public void testDoGenericRequestWithoutAValidConnection() throws FogbowException {
		// set up
		HttpMethod httpMethod = HttpMethod.GET;
		String validUrl = DEFAULT_URL;

		Map<String, String> body = new HashMap<String, String>();
		body.put(ANY_VALUE, ANY_VALUE);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put(ANY_VALUE, ANY_VALUE);

		// exercise
		this.client.doGenericRequest(httpMethod, validUrl, headers, body);
	}
}
