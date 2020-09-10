package cloud.fogbow.common.util.connectivity.cloud;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.fogbow.common.exceptions.FogbowException;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cloud.fogbow.common.constants.CloudStackConstants;
import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.models.CloudUser;
import cloud.fogbow.common.util.connectivity.HttpRequest;
import cloud.fogbow.common.util.connectivity.HttpRequestClient;
import cloud.fogbow.common.util.connectivity.HttpResponse;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ HttpRequestClient.class })
public class CloudHttpClientTest {

    private static final String ANY_VALUE = "anything";
    private static final String BODY_CONTENT_JSON = "{\"content\": \"anything\"}";
    private static final String COMPLETE_MESSAGE_FORMAT = "status code: %s, reason phrase: %s";
    private static final String DEFAULT_URL = "http://localhost:8080";
    private static final String STATUS_CODE_MESSAGE_FORMAT = "status code: %s";
    private static final int RUN_ONCE = 1;

    CloudHttpClient<CloudUser> client;
    CloudUser cloudUser;

    @Before
    public void setup() {
        this.client = Mockito.mock(CloudHttpClient.class);
        this.cloudUser = Mockito.mock(CloudUser.class);
    }

    // test case: When calling the doGetRequest method, it must verify if
    // the call was successful.
    @Test
    public void testDoGetRequestSuccessfully() throws Exception {
        // set up
        String url = DEFAULT_URL;
        Mockito.doCallRealMethod().when(this.client).doGetRequest(Mockito.eq(url),
                Mockito.eq(this.cloudUser));

        String content = ANY_VALUE;
        HttpMethod method = HttpMethod.GET;
        String emptyBody = CloudHttpClient.EMPTY_BODY;
        Mockito.when(this.client.callDoGenericRequest(Mockito.eq(method), Mockito.eq(url),
                Mockito.eq(emptyBody), Mockito.eq(this.cloudUser))).thenReturn(content);
    
        // exercise
        this.client.doGetRequest(url, this.cloudUser);

        // verify
        Mockito.verify(this.client, Mockito.times(RUN_ONCE)).callDoGenericRequest(Mockito.eq(method),
                Mockito.eq(url), Mockito.eq(emptyBody), Mockito.eq(this.cloudUser));
    }

    // test case: When calling the doDeleteRequest method, it must verify if
    // the call was successful.
    @Test
    public void testDoDeleteRequestSuccessfully() throws Exception {
        // set up
        String url = DEFAULT_URL;
        Mockito.doCallRealMethod().when(this.client).doDeleteRequest(Mockito.eq(url),
                Mockito.eq(this.cloudUser));

        String content = ANY_VALUE;
        HttpMethod method = HttpMethod.DELETE;
        String emptyBody = CloudHttpClient.EMPTY_BODY;
        Mockito.when(this.client.callDoGenericRequest(Mockito.eq(method), Mockito.eq(url),
                Mockito.eq(emptyBody), Mockito.eq(this.cloudUser))).thenReturn(content);

        // exercise
        this.client.doDeleteRequest(url, cloudUser);

        // verify
        Mockito.verify(this.client, Mockito.times(RUN_ONCE)).callDoGenericRequest(Mockito.eq(method),
                Mockito.eq(url), Mockito.eq(emptyBody), Mockito.eq(this.cloudUser));
    }

    // test case: When calling the doPostRequest method, it must verify if
    // the call was successful.
    @Test
    public void testDoPostRequestSuccessFully() throws Exception {
        // set up
        String url = DEFAULT_URL;
        String bodyContent = BODY_CONTENT_JSON;
        Mockito.doCallRealMethod().when(this.client).doPostRequest(Mockito.eq(url),
                Mockito.eq(bodyContent), Mockito.eq(this.cloudUser));

        String content = ANY_VALUE;
        HttpMethod method = HttpMethod.POST;
        Mockito.when(this.client.callDoGenericRequest(Mockito.eq(method), Mockito.eq(url),
                Mockito.eq(bodyContent), Mockito.eq(this.cloudUser))).thenReturn(content);

        // exercise
        this.client.doPostRequest(url, bodyContent, cloudUser);

        // verify
        Mockito.verify(this.client, Mockito.times(RUN_ONCE)).callDoGenericRequest(Mockito.eq(method),
                Mockito.eq(url), Mockito.eq(bodyContent), Mockito.eq(this.cloudUser));
    }

    // test case: When calling the callDoGenericRequest method with content response
    // status codes, it must verify that the call was successful.
    @Test
    public void testCallDoGenericRequestSuccessfully() throws Exception {
        // set up
        HttpMethod method = HttpMethod.POST;
        String url = DEFAULT_URL;
        String bodyContent = BODY_CONTENT_JSON;
        Mockito.doCallRealMethod().when(this.client).callDoGenericRequest(Mockito.eq(method),
                Mockito.eq(url), Mockito.eq(bodyContent), Mockito.eq(this.cloudUser));

        HttpResponse response = Mockito.mock(HttpResponse.class);
        Mockito.when(this.client.doGenericRequest(Mockito.eq(method), Mockito.eq(url),
                Mockito.anyMap(), Mockito.anyMap(), Mockito.eq(this.cloudUser))).thenReturn(response );

        // exercise
        this.client.callDoGenericRequest(method, url, bodyContent, this.cloudUser);

        // verify
        Mockito.verify(this.client, Mockito.times(RUN_ONCE)).doGenericRequest(Mockito.eq(method), Mockito.eq(url),
                Mockito.anyMap(), Mockito.anyMap(), Mockito.eq(this.cloudUser));

        Mockito.verify(response, Mockito.times(RUN_ONCE)).getContent();
    }

    // test case: When calling the callDoGenericRequest method with non-content
    // response status codes, it must verify that the FogbowException was
    // thrown.
    @Test
    public void testCallDoGenericRequestFail() throws Exception {
        // set up
        HttpMethod method = HttpMethod.POST;
        String url = DEFAULT_URL;
        String bodyContent = BODY_CONTENT_JSON;
        Mockito.doCallRealMethod().when(this.client).callDoGenericRequest(Mockito.eq(method),
                Mockito.eq(url), Mockito.eq(bodyContent), Mockito.eq(this.cloudUser));

        HttpResponse response = Mockito.mock(HttpResponse.class);
        Mockito.when(response.getHttpCode()).thenReturn(HttpStatus.SC_FORBIDDEN);

        Mockito.when(this.client.doGenericRequest(Mockito.eq(method), Mockito.eq(url),
                Mockito.anyMap(), Mockito.anyMap(), Mockito.eq(this.cloudUser))).thenReturn(response );

        try {
            // exercise
            this.client.callDoGenericRequest(method, url, bodyContent, this.cloudUser);
            Assert.fail();
        } catch (FogbowException e) {
            // verify
            Assert.assertEquals(null, e.getMessage());
        }
    }

    // test case: When calling the callDoGenericRequest method with a specific
    // response status code, it must verify if the message thrown in the
    // HttpResponseException, contains the error description obtained in the
    // response headers.
    @Test
    public void testCallDoGenericRequestWithSpecificFail() throws Exception {
        // set up
        HttpMethod method = HttpMethod.POST;
        String url = DEFAULT_URL;
        String bodyContent = BODY_CONTENT_JSON;
        Mockito.doCallRealMethod().when(this.client).callDoGenericRequest(Mockito.eq(method),
                Mockito.eq(url), Mockito.eq(bodyContent), Mockito.eq(this.cloudUser));

        HttpResponse response = Mockito.mock(HttpResponse.class);

        Mockito.when(this.client.getMessageFrom(Mockito.anyMap())).thenReturn(ANY_VALUE);       
        int statusCode = CloudHttpClient.SC_REQUEST_HEADER_FIELDS_TOO_LARGE;
        Mockito.when(response.getHttpCode()).thenReturn(statusCode);

        Mockito.when(this.client.doGenericRequest(Mockito.eq(method), Mockito.eq(url),
                Mockito.anyMap(), Mockito.anyMap(), Mockito.eq(this.cloudUser))).thenReturn(response );

        try {
            // exercise
            this.client.callDoGenericRequest(method, url, bodyContent, this.cloudUser);
            Assert.fail();
        } catch (FogbowException e) {
            // verify
            Assert.assertEquals(ANY_VALUE, e.getMessage());
        }
    }

    // test case: When calling the getMessageFrom method with valid response
    // Headers, it must verify if the call was successful.
    @Test
    public void testGetMessageFromResponseHeadersSuccessfully() {
        // set up
        String expected = ANY_VALUE;
        String headerKey = CloudStackConstants.X_DESCRIPTION_KEY;
        String[] headerValues = { expected };
        Map<String, List<String>> responseHeaders = new HashMap<String, List<String>>();
        responseHeaders.put(headerKey, Arrays.asList(headerValues));

        Mockito.doCallRealMethod().when(this.client).getMessageFrom(Mockito.eq(responseHeaders));

        // exercise
        String message = this.client.getMessageFrom(responseHeaders);

        // verify
        Assert.assertEquals(expected, message);
    }

    // test case: When calling the doGenericRequest method, it must verify if the
    // call was successful.
    @Test
    public void testDoGenericRequestSuccessfully() throws Exception {
        // set up
        HttpMethod method = HttpMethod.POST;
        String url = DEFAULT_URL;
        Map<String, String> body = new HashMap<String, String>();
        Map<String, String> headers = new HashMap<String, String>();

        Mockito.doCallRealMethod().when(this.client).doGenericRequest(Mockito.eq(method),
                Mockito.eq(url), Mockito.eq(headers), Mockito.eq(body),
                Mockito.eq(this.cloudUser));

        HttpRequest request = Mockito.mock(HttpRequest.class);
        Mockito.when(request.getMethod()).thenReturn(method);
        Mockito.when(request.getUrl()).thenReturn(url);

        Mockito.when(this.client.createHttpRequest(Mockito.eq(method), Mockito.eq(url),
                Mockito.eq(body), Mockito.eq(headers))).thenReturn(request);

        Mockito.when(this.client.prepareRequest(Mockito.eq(request),
                Mockito.eq(this.cloudUser))).thenReturn(request);

        HttpResponse response = Mockito.mock(HttpResponse.class);
        PowerMockito.mockStatic(HttpRequestClient.class);
        PowerMockito.doReturn(response).when(HttpRequestClient.class, "doGenericRequest",
                Mockito.eq(method), Mockito.eq(url), Mockito.eq(headers), Mockito.eq(body));

        // exercise
        this.client.doGenericRequest(method, url, headers, body, this.cloudUser);

        // verify
        Mockito.verify(this.client, Mockito.times(RUN_ONCE)).createHttpRequest(Mockito.eq(method),
                Mockito.eq(url), Mockito.eq(body), Mockito.eq(headers));

        Mockito.verify(this.client, Mockito.times(RUN_ONCE)).prepareRequest(Mockito.eq(request),
                Mockito.eq(this.cloudUser));

        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.doGenericRequest(Mockito.eq(method), Mockito.eq(url),
                Mockito.eq(headers), Mockito.eq(body));
    }

}
