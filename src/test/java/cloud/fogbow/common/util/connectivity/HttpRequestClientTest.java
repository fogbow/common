package cloud.fogbow.common.util.connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.fogbow.common.exceptions.InternalServerErrorException;
import cloud.fogbow.common.exceptions.UnavailableProviderException;
import cloud.fogbow.common.util.connectivity.HttpErrorConditionToFogbowExceptionMapper;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ HttpErrorConditionToFogbowExceptionMapper.class, HttpRequestClient.class })
public class HttpRequestClientTest {

    private static final String ANY_VALUE = "anything";
    private static final String DEFAULT_URL = "http://localhost:8080";
    private static final int RUN_ONCE = 1;
	
    // test case: When invoking the doGenericRequest method with an invalid URL,
    // it must throw the InvalidParameterException.
    @Test(expected = InvalidParameterException.class) // verify
    public void testDoGenericRequestWithInvalidURL() throws FogbowException {
        // set up
        HttpMethod httpMethod = HttpMethod.GET;
        String invalidUrl = ANY_VALUE;
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> body = new HashMap<String, String>();

        // exercise
        HttpRequestClient.doGenericRequest(httpMethod, invalidUrl, headers, body);
    }

    // test case: When invoking the doGenericRequest method with a valid URL,
    // but can not open a connection through this URL, it must throw the
    // UnavailableProviderException.
    @Test(expected = UnavailableProviderException.class) // verify
    public void testDoGenericRequestWithoutAValidConnection() throws FogbowException {
        // set up
        HttpMethod httpMethod = HttpMethod.GET;
        String validUrl = DEFAULT_URL;

        Map<String, String> body = new HashMap<String, String>();
        body.put(ANY_VALUE, ANY_VALUE);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(ANY_VALUE, ANY_VALUE);

        // exercise
        HttpRequestClient.doGenericRequest(httpMethod, validUrl, headers, body);
    }

    // test case: When calling the doGenericRequest method, it must verify if
    // the call was successful.
    @Test
    public void testDoGenericRequestSuccessfully() throws Exception {
        // set up
        HttpMethod method = HttpMethod.GET;
        String endpoint = DEFAULT_URL;
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> body = new HashMap<String, String>();

        PowerMockito.mockStatic(HttpRequestClient.class);
        PowerMockito.doCallRealMethod().when(HttpRequestClient.class, "doGenericRequest",
                Mockito.eq(method), Mockito.eq(endpoint), Mockito.eq(headers),
                Mockito.eq(body));

        HttpURLConnection connection = Mockito.mock(HttpURLConnection.class);
        PowerMockito.doReturn(connection).when(HttpRequestClient.class, "prepareConnection",
                Mockito.eq(endpoint), Mockito.eq(method), Mockito.eq(headers));

        PowerMockito.doNothing().when(HttpRequestClient.class, "sendRequestBody",
                Mockito.eq(connection), Mockito.eq(body));

        HttpResponse response = Mockito.mock(HttpResponse.class);
        PowerMockito.doReturn(response).when(HttpRequestClient.class, "getHttpResponse",
                Mockito.eq(connection));

        // exercise
        HttpRequestClient.doGenericRequest(method, endpoint, headers, body);

        // verify
        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.prepareConnection(Mockito.eq(endpoint), Mockito.eq(method),
                Mockito.eq(headers));

        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.sendRequestBody(Mockito.eq(connection), Mockito.eq(body));

        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.getHttpResponse(Mockito.eq(connection));
    }

    // test case: When calling the getHttpResponse method, it must verify if the
    // call was successful.
    @Test
    public void testGetHttpResponseSuccessfully() throws Exception {
        // set up
        HttpURLConnection connection = Mockito.mock(HttpURLConnection.class);
        PowerMockito.mockStatic(HttpRequestClient.class);
        PowerMockito.doCallRealMethod().when(HttpRequestClient.class, "getHttpResponse",
                Mockito.eq(connection));

        int responseCode = HttpStatus.SC_OK;
        Mockito.when(connection.getResponseCode()).thenReturn(responseCode);

        Map<String, List<String>> responseHeaders = new HashMap<String, List<String>>();
        Mockito.when(connection.getHeaderFields()).thenReturn(responseHeaders);

        String responseBody = ANY_VALUE;
        PowerMockito.doReturn(responseBody).when(HttpRequestClient.class, "getResponseBody",
                Mockito.eq(connection));

        // exercise
        HttpRequestClient.getHttpResponse(connection);

        // verify
        Mockito.verify(connection, Mockito.times(RUN_ONCE)).getResponseCode();
        Mockito.verify(connection, Mockito.times(RUN_ONCE)).getHeaderFields();

        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.getResponseBody(Mockito.eq(connection));
    }

    // test case: When calling the getHttpResponse method and an error occurs,
    // it must verify that an UnespectedException has been thrown and the map
    // method from HttpErrorConditionToFogbowExceptionMapper class was called.
    @Test
    public void testGetHttpResponseFail() throws Exception {
        // set up
        HttpURLConnection connection = Mockito.mock(HttpURLConnection.class);
        PowerMockito.mockStatic(HttpRequestClient.class);
        PowerMockito.doCallRealMethod().when(HttpRequestClient.class, "getHttpResponse",
                Mockito.eq(connection));

        String message = ANY_VALUE;
        IOException exception = new IOException(message);
        Mockito.when(connection.getResponseCode()).thenThrow(exception);

        int expectedHttpStatus = HttpRequestClient.INVALID_HTTP_STATUS_CODE;
        PowerMockito.mockStatic(HttpErrorConditionToFogbowExceptionMapper.class);
        PowerMockito.doReturn(new InternalServerErrorException())
                .when(HttpErrorConditionToFogbowExceptionMapper.class, "map", Mockito.anyInt(),
                Mockito.anyString());
        try {
            // exercise
            HttpRequestClient.getHttpResponse(connection);
            Assert.fail();
        } catch (InternalServerErrorException e) {
            // Verify
            PowerMockito.verifyStatic(HttpErrorConditionToFogbowExceptionMapper.class, Mockito.times(RUN_ONCE));
            HttpErrorConditionToFogbowExceptionMapper.map(Mockito.eq(expectedHttpStatus), Mockito.eq(message));
        }
    }

    // test case: When calling the getResponseBody method, it must verify if the
    // call was successful.
    @Test
    public void testGetResponseBodySuccessfully() throws Exception {
        // set up
        HttpURLConnection connection = Mockito.mock(HttpURLConnection.class);
        PowerMockito.mockStatic(HttpRequestClient.class);
        PowerMockito.doCallRealMethod().when(HttpRequestClient.class, "getResponseBody",
                Mockito.eq(connection));

        InputStream inputStream = Mockito.mock(InputStream.class);
        Mockito.when(connection.getInputStream()).thenReturn(inputStream);

        String response = ANY_VALUE;
        PowerMockito.doReturn(response).when(HttpRequestClient.class, "getResponseFrom",
                Mockito.eq(inputStream));

        // exercise
        HttpRequestClient.getResponseBody(connection);

        // verify
        Mockito.verify(connection, Mockito.times(RUN_ONCE)).getInputStream();

        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.getResponseFrom(Mockito.eq(inputStream));
    }

    // test case: When calling the getResponseFrom method with a valid input
    // stream object, it must verify if the call was successful.
    @Test
    public void testGetResponseFromInputStreamSuccessfully() throws Exception {
        // set up
        InputStream inputStream = Mockito.mock(InputStream.class);
        PowerMockito.mockStatic(HttpRequestClient.class);
        PowerMockito.doCallRealMethod().when(HttpRequestClient.class, "getResponseFrom",
                Mockito.eq(inputStream));

        InputStreamReader inputStreamReader = Mockito.mock(InputStreamReader.class);
        PowerMockito.doReturn(inputStreamReader).when(HttpRequestClient.class, "createInputStreamReader",
                Mockito.eq(inputStream));

        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        PowerMockito.doReturn(bufferedReader).when(HttpRequestClient.class, "createBufferedReader",
                Mockito.eq(inputStreamReader));

        // exercise
        HttpRequestClient.getResponseFrom(inputStream);

        // verify
        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.createInputStreamReader(Mockito.eq(inputStream));

        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.createBufferedReader(Mockito.eq(inputStreamReader));

        Mockito.verify(bufferedReader, Mockito.times(RUN_ONCE)).readLine();
        Mockito.verify(bufferedReader, Mockito.times(RUN_ONCE)).close();
    }

    // test case: When calling the sendRequestBody method with a valid
    // connection and a body non-empty, it must verify that if the call was
    // successful.
    @Test
    public void testSendRequestBodySuccessfully() throws Exception {
        // set up
        HttpURLConnection connection = Mockito.mock(HttpURLConnection.class);
        Map<String, String> body = new HashMap<String, String>();
        body.put(ANY_VALUE, DEFAULT_URL);
        byte[] bytes = null;

        PowerMockito.mockStatic(HttpRequestClient.class);
        PowerMockito.doCallRealMethod().when(HttpRequestClient.class, "sendRequestBody",
                Mockito.eq(connection), Mockito.eq(body));

        OutputStream outputStream = Mockito.mock(OutputStream.class);
        Mockito.when(connection.getOutputStream()).thenReturn(outputStream);

        // exercise
        HttpRequestClient.sendRequestBody(connection, body);

        // verify
        Mockito.verify(connection, Mockito.times(RUN_ONCE)).setDoOutput(Mockito.anyBoolean());
        Mockito.verify(connection, Mockito.times(RUN_ONCE)).getOutputStream();

        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.toByteArray(Mockito.eq(body));

        Mockito.verify(outputStream, Mockito.times(RUN_ONCE)).write(Mockito.eq(bytes));
        Mockito.verify(outputStream, Mockito.times(RUN_ONCE)).flush();
        Mockito.verify(outputStream, Mockito.times(RUN_ONCE)).close();
    }

    // test case: When calling the prepareConnection method, it must verify if
    // the call was successful.
    @Test
    public void testPrepareConnectionSuccessfully() throws Exception {
        // set up
        HttpMethod method = HttpMethod.GET;
        String endpoint = DEFAULT_URL;
        Map<String, String> headers = new HashMap<String, String>();

        PowerMockito.mockStatic(HttpRequestClient.class);
        PowerMockito.doCallRealMethod().when(HttpRequestClient.class, "prepareConnection",
                Mockito.eq(endpoint), Mockito.eq(method), Mockito.eq(headers));

        URL url = PowerMockito.mock(URL.class);
        PowerMockito.doReturn(url).when(HttpRequestClient.class, "createConnectionUrl",
                Mockito.eq(endpoint));

        HttpURLConnection connection = Mockito.mock(HttpURLConnection.class);
        PowerMockito.doReturn(connection).when(HttpRequestClient.class, "openConnection",
                Mockito.eq(url));

        PowerMockito.doNothing().when(HttpRequestClient.class, "setMethodIntoConnection",
                Mockito.eq(connection), Mockito.eq(method));

        PowerMockito.doNothing().when(HttpRequestClient.class, "addHeadersIntoConnection",
                Mockito.eq(connection), Mockito.eq(headers));

        // exercise
        HttpRequestClient.prepareConnection(endpoint, method, headers);

        // verify
        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.createConnectionUrl(Mockito.eq(endpoint));

        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.openConnection(Mockito.eq(url));

        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.setMethodIntoConnection(Mockito.eq(connection),
                Mockito.eq(method));

        PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(RUN_ONCE));
        HttpRequestClient.addHeadersIntoConnection(Mockito.eq(connection),
                Mockito.eq(headers));
    }

    // test case: When calling the setMethodIntoConnection method, it must
    // verify that if the call was successful.
    @Test
    public void testSetMethodIntoConnectionSuccessfully() throws Exception {
        // set up
        HttpURLConnection connection = Mockito.mock(HttpURLConnection.class);
        HttpMethod method = HttpMethod.GET;
        PowerMockito.mockStatic(HttpRequestClient.class);
        PowerMockito.doCallRealMethod().when(HttpRequestClient.class, "setMethodIntoConnection",
                Mockito.eq(connection), Mockito.eq(method));

        String methodName = method.getName();
        Mockito.doNothing().when(connection).setRequestMethod(Mockito.eq(methodName));

        // exercise
        HttpRequestClient.setMethodIntoConnection(connection, method);

        // verify
        Mockito.verify(connection, Mockito.times(RUN_ONCE))
                .setRequestMethod(Mockito.eq(methodName));
    }

    // test case: When calling the setMethodIntoConnection method and an error
    // occurs, it must verify that an InternalServerErrorException has been thrown.
    @Test
    public void testSetMethodIntoConnectionFail() throws Exception {
        // set up
        HttpURLConnection connection = Mockito.mock(HttpURLConnection.class);
        HttpMethod method = HttpMethod.GET;
        PowerMockito.mockStatic(HttpRequestClient.class);
        PowerMockito.doCallRealMethod().when(HttpRequestClient.class, "setMethodIntoConnection",
                Mockito.eq(connection), Mockito.eq(method));

        String message = ANY_VALUE;
        ProtocolException exception = new ProtocolException(message);
        Mockito.doThrow(exception).when(connection).setRequestMethod(Mockito.anyString());

        try {
            // exercise
            HttpRequestClient.setMethodIntoConnection(connection, method);
            Assert.fail();
        } catch (InternalServerErrorException e) {
            // verify
            Assert.assertEquals(message, e.getMessage());
        }
    }

    // test case: When calling the openConnection method and an error occurs, it
    // must verify that an InternalServerErrorException has been thrown.
    @Test
    public void testOpenConnectionFail() throws Exception {
        // set up
        URL url = PowerMockito.mock(URL.class);
        PowerMockito.mockStatic(HttpRequestClient.class);
        PowerMockito.doCallRealMethod().when(HttpRequestClient.class, "openConnection",
                Mockito.eq(url));

        String message = ANY_VALUE;
        IOException exception = new IOException(message);
        Mockito.when(url.openConnection()).thenThrow(exception);

        try {
            // exercise
            HttpRequestClient.openConnection(url);
            Assert.fail();
        } catch (InternalServerErrorException e) {
            // verify
            Assert.assertEquals(message, e.getMessage());
        }
    }

    // test case: When calling the createConnectionUrl method and an error
    // occurs, it must verify that an InvalidParameterException has been thrown.
    @Test
    public void testCreateConnectionUrlFail() throws FogbowException {
        // set up
        String endpoint = ANY_VALUE;
        String message = "no protocol: anything";

        try {
            // exercise
            HttpRequestClient.createConnectionUrl(endpoint);
            Assert.fail();
        } catch (InvalidParameterException e) {
            // verify
            Assert.assertEquals(message, e.getMessage());
        }
    }

}
