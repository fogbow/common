package cloud.fogbow.common.util.connectivity;

import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.util.GsonHolder;
import cloud.fogbow.common.util.HttpErrorToFogbowExceptionMapper;

import org.apache.log4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpRequestClient {

    private static final Logger LOGGER = Logger.getLogger(HttpRequestClient.class);
    private static final int UNDISCERNED_HTTP_STATUS_CODE = -1;

    public static HttpResponse doGenericRequest(HttpMethod method, String endpoint, Map<String, String> headers,
            Map<String, String> body) throws FogbowException {

        HttpURLConnection connection = prepareConnection(endpoint, method, headers);
        sendRequestBody(connection, body);
        return getHttpResponse(connection);
    }

    @VisibleForTesting
    static HttpResponse getHttpResponse(HttpURLConnection connection) throws FogbowException {
        int responseCode = UNDISCERNED_HTTP_STATUS_CODE;
        try {
            responseCode = connection.getResponseCode();
            Map<String, List<String>> responseHeaders = connection.getHeaderFields();
            String responseBody = getResponseBody(connection);
            return new HttpResponse(responseBody, responseCode, responseHeaders);
        } catch (IOException e) {
            throw HttpErrorToFogbowExceptionMapper.map(responseCode, e.getMessage());
        }
    }

    @VisibleForTesting
    static String getResponseBody(HttpURLConnection connection) {
        String response = null;
        try {
            InputStream inputStream = connection.getInputStream();
            response = getResponseFrom(inputStream);
        } catch (IOException e) {
            LOGGER.error(String.format(Messages.Error.ERROR_MESSAGE_IS_S, e.getMessage()), e);
        }
        return response;
    }

    @VisibleForTesting
    static String getResponseFrom(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = createInputStreamReader(inputStream);
        BufferedReader bufferedReader = createBufferedReader(inputStreamReader);
        StringBuffer responseBuffer = new StringBuffer();
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            responseBuffer.append(inputLine);
        }
        bufferedReader.close();
        return responseBuffer.toString();
    }

    @VisibleForTesting
    static BufferedReader createBufferedReader(InputStreamReader inputStreamReader) {
        return new BufferedReader(inputStreamReader);
    }

    @VisibleForTesting
    static InputStreamReader createInputStreamReader(InputStream inputStream) {
        return new InputStreamReader(inputStream);
    }

    @VisibleForTesting
    static void sendRequestBody(HttpURLConnection connection, Map<String, String> body) throws FogbowException {
        if (!body.isEmpty()) {
            connection.setDoOutput(true);
            try {
                OutputStream os = connection.getOutputStream();
                os.write(toByteArray(body));
                os.flush();
                os.close();
            } catch (IOException e) {
                throw new UnexpectedException(e.getMessage());
            }
        }
    }

    @VisibleForTesting
    static byte[] toByteArray(Map<String, String> body) {
        String json = GsonHolder.getInstance().toJson(body, Map.class);
        return json.getBytes();
    }

    @VisibleForTesting
    static HttpURLConnection prepareConnection(String endpoint, HttpMethod method, Map<String, String> headers)
            throws FogbowException {

        HttpURLConnection connection = openConnection(endpoint);
        setMethodIntoConnection(connection, method);
        addHeadersIntoConnection(connection, headers);
        return connection;
    }

    @VisibleForTesting
    static void addHeadersIntoConnection(HttpURLConnection connection, Map<String, String> headers) {
        for (String key : headers.keySet()) {
            connection.setRequestProperty(key, headers.get(key));
        }
    }

    @VisibleForTesting
    static void setMethodIntoConnection(HttpURLConnection connection, HttpMethod method) throws FogbowException {
        try {
            String methodName = method.getName();
            connection.setRequestMethod(methodName);
        } catch (ProtocolException e) {
            throw new InvalidParameterException(e.getMessage(), e);
        }
    }

    @VisibleForTesting
    static HttpURLConnection openConnection(String endpoint) throws FogbowException {
        URL url = createUrlEndpoint(endpoint);
        try {
            return (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new UnexpectedException(e.getMessage());
        }
    }

    @VisibleForTesting
    static URL createUrlEndpoint(String endpoint) throws FogbowException {
        try {
            return new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new InvalidParameterException(e.getMessage(), e);
        }
    }

}
