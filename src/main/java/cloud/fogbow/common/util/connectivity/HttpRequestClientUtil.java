package cloud.fogbow.common.util.connectivity;

import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.models.CloudToken;
import cloud.fogbow.common.util.GsonHolder;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestClientUtil {
    private static final Logger LOGGER = Logger.getLogger(HttpRequestClientUtil.class);

//    public String doGetRequest(String endpoint, CloudToken tokenValue)
//            throws UnavailableProviderException, HttpResponseException {
//        HttpGet request = new HttpGet(endpoint);
//        request.addHeader(HttpRequestUtil.CONTENT_TYPE_KEY, HttpRequestUtil.JSON_CONTENT_TYPE_KEY);
//        request.addHeader(HttpRequestUtil.ACCEPT_KEY, HttpRequestUtil.JSON_CONTENT_TYPE_KEY);
//        request.addHeader(HttpRequestUtil.X_AUTH_TOKEN_KEY, tokenValue.getTokenValue());
//
//        HttpResponse httpResponse = null;
//        String response = null;
//        String status = null;
//
//        try {
//            LOGGER.debug(String.format("making GET request on <%s> with token <%s>", endpoint, tokenValue));
//
//            httpResponse = this.client.execute(request);
//            if (httpResponse.getEntity() != null) {
//            	response = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//            }
//
//            if (httpResponse.getStatusLine().getStatusCode() > HttpStatus.NO_CONTENT.value()) {
//            	status = httpResponse.getStatusLine().getReasonPhrase();
//            	String message = response == null ? status : response;
//                throw new HttpResponseException(httpResponse.getStatusLine().getStatusCode(), message);
//            }
//        } catch (HttpResponseException e) {
//            LOGGER.debug(String.format("error was <%s>", e.toString()), e);
//            throw e;
//        } catch (IOException e) {
//            throw new UnavailableProviderException(e.getMessage(), e);
//        } finally {
//            try {
//                EntityUtils.consume(httpResponse.getEntity());
//                LOGGER.debug(String.format("response was: <%s>", httpResponse));
//            } catch (Throwable t) {
//                LOGGER.error(String.format(Messages.Error.ERROR_WHILE_CONSUMING_RESPONSE, t));
//            }
//        }
//        return response;
//    }
//
//    public String doPostRequestOld(String endpoint, String body, CloudToken tokenValue)
//            throws UnavailableProviderException, HttpResponseException {
//        HttpPost request = new HttpPost(endpoint);
//        request.addHeader(HttpRequestUtil.CONTENT_TYPE_KEY, HttpRequestUtil.JSON_CONTENT_TYPE_KEY);
//        request.addHeader(HttpRequestUtil.ACCEPT_KEY, HttpRequestUtil.JSON_CONTENT_TYPE_KEY);
//        request.addHeader(HttpRequestUtil.X_AUTH_TOKEN_KEY, tokenValue.getTokenValue());
//        request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
//
//        HttpResponse httpResponse = null;
//        String response = null;
//        String status = null;
//
//        try {
//            LOGGER.debug(String.format("making GET request on <%s> with token <%s>", endpoint, tokenValue));
//            LOGGER.debug(String.format("the body of the request is <%s>", body));
//
//            httpResponse = this.client.execute(request);
//            if (httpResponse.getEntity() != null) {
//            	response = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//            }
//
//            if (httpResponse.getStatusLine().getStatusCode() > HttpStatus.NO_CONTENT.value()) {
//            	status = httpResponse.getStatusLine().getReasonPhrase();
//            	String message = response == null ? status : response;
//                throw new HttpResponseException(httpResponse.getStatusLine().getStatusCode(), message);
//            }
//        } catch (HttpResponseException e) {
//            LOGGER.debug(String.format("error was: <%s>", e.toString()), e);
//            throw e;
//        } catch (IOException e) {
//            throw new UnavailableProviderException(e.getMessage(), e);
//        } finally {
//            try {
//                EntityUtils.consume(httpResponse.getEntity());
//                LOGGER.debug(String.format("response was: <%s>", httpResponse));
//            } catch (Throwable t) {
//                LOGGER.error(String.format(Messages.Error.ERROR_WHILE_CONSUMING_RESPONSE, t));
//            }
//        }
//        return response;
//    }
//
//    public void doDeleteRequest(String endpoint, CloudToken tokenValue)
//            throws FogbowException, HttpResponseException {
//        HttpDelete request = new HttpDelete(endpoint);
//        request.addHeader(HttpRequestUtil.X_AUTH_TOKEN_KEY, tokenValue.getTokenValue());
//
//        HttpResponse httpResponse = null;
//        String response = null;
//        String status = null;
//
//        try {
//            LOGGER.debug(String.format("making DELETE request on <%s> with token <%s>", endpoint, tokenValue));
//
//            httpResponse = this.client.execute(request);
//            if (httpResponse.getEntity() != null) {
//            	response = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//            }
//
//            if (httpResponse.getStatusLine().getStatusCode() > HttpStatus.NO_CONTENT.value()) {
//            	status = httpResponse.getStatusLine().getReasonPhrase();
//            	String message = response == null ? status : response;
//                throw new HttpResponseException(httpResponse.getStatusLine().getStatusCode(), message);
//            }
//        } catch (HttpResponseException e) {
//            LOGGER.debug(String.format("error was: <%s>", e.toString()), e);
//            throw e;
//        } catch (IOException e) {
//            throw new UnavailableProviderException(e.getMessage(), e);
//        } finally {
//            try {
//                EntityUtils.consume(httpResponse.getEntity());
//                LOGGER.debug(String.format("response was: <%s>", httpResponse));
//            } catch (Throwable t) {
//                LOGGER.error(String.format(Messages.Error.ERROR_WHILE_CONSUMING_RESPONSE, t));
//            }
//        }
//    }
//
//    public Response doPostRequest(String endpoint, String body)
//            throws HttpResponseException, UnavailableProviderException {
//        HttpPost request = new HttpPost(endpoint);
//        request.addHeader(HttpRequestUtil.CONTENT_TYPE_KEY, HttpRequestUtil.JSON_CONTENT_TYPE_KEY);
//        request.addHeader(HttpRequestUtil.ACCEPT_KEY, HttpRequestUtil.JSON_CONTENT_TYPE_KEY);
//        request.setEntity(new StringEntity(body.toString(), StandardCharsets.UTF_8));
//
//        HttpResponse httpResponse = null;
//        String response = null;
//        String status = null;
//
//        try {
//            httpResponse = this.client.execute(request);
//            if (httpResponse.getEntity() != null) {
//            	response = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//            }
//
//            if (httpResponse.getStatusLine().getStatusCode() > HttpStatus.NO_CONTENT.value()) {
//            	status = httpResponse.getStatusLine().getReasonPhrase();
//            	String message = response == null ? status : response;
//                throw new HttpResponseException(httpResponse.getStatusLine().getStatusCode(), message);
//            }
//        } catch (HttpResponseException e) {
//        	LOGGER.debug(String.format("error was: <%s>", e.toString()), e);
//        	throw e;
//        } catch (IOException e) {
//            throw new UnavailableProviderException(e.getMessage(), e);
//        } finally {
//            try {
//                EntityUtils.consume(httpResponse.getEntity());
//                LOGGER.debug(String.format("response was: <%s>", httpResponse));
//            } catch (Throwable t) {
//                LOGGER.error(String.format(Messages.Error.ERROR_WHILE_CONSUMING_RESPONSE, t));
//            }
//        }
//        return new Response(response, httpResponse.getAllHeaders());
//    }
//
//    public String doPutRequest(String endpoint, CloudToken tokenValue, JSONObject json)
//            throws HttpResponseException, UnavailableProviderException {
//        HttpPut request = new HttpPut(endpoint);
//        request.addHeader(HttpRequestUtil.CONTENT_TYPE_KEY, HttpRequestUtil.JSON_CONTENT_TYPE_KEY);
//        request.addHeader(HttpRequestUtil.ACCEPT_KEY, HttpRequestUtil.JSON_CONTENT_TYPE_KEY);
//        request.addHeader(HttpRequestUtil.X_AUTH_TOKEN_KEY, tokenValue.getTokenValue());
//        request.setEntity(new StringEntity(json.toString(), StandardCharsets.UTF_8));
//
//        HttpResponse httpResponse = null;
//        String response = null;
//        String status = null;
//
//        try {
//            httpResponse = this.client.execute(request);
//            if (httpResponse.getEntity() != null) {
//            	response = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//            }
//
//            if (httpResponse.getStatusLine().getStatusCode() > HttpStatus.NO_CONTENT.value()) {
//            	status = httpResponse.getStatusLine().getReasonPhrase();
//            	String message = response == null ? status : response;
//                throw new HttpResponseException(httpResponse.getStatusLine().getStatusCode(), message);
//            }
//        } catch (HttpResponseException e) {
//        	LOGGER.debug(String.format("error was: <%s>", e.toString()), e);
//        	throw e;
//        } catch (IOException e) {
//            throw new UnavailableProviderException(e.getMessage(), e);
//        } finally {
//            try {
//                EntityUtils.consume(httpResponse.getEntity());
//            } catch (Throwable t) {
//                LOGGER.error(String.format(Messages.Error.ERROR_WHILE_CONSUMING_RESPONSE, t));
//            }
//        }
//        return response;
//    }

    public GenericRequestHttpResponse doGenericRequest(String method, String urlString,
                   HashMap<String, String> headers, HashMap<String, String> body, CloudToken token)
                   throws FogbowException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method.toUpperCase());

            addHeadersIntoConnection(connection, headers);

            if (!body.isEmpty()) {
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                os.write(toByteArray(body));
                os.flush();
                os.close();
            }

            int responseCode = connection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            StringBuffer responseBuffer = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
            in.close();

            Map<String, List<String>> responseHeaders = connection.getHeaderFields();
            return new GenericRequestHttpResponse(responseBuffer.toString(), responseCode, responseHeaders);
        } catch (ProtocolException e) {
            throw new FogbowException("", e);
        } catch (MalformedURLException e) {
            throw new FogbowException("", e);
        } catch (IOException e) {
            throw new FogbowException("", e);
        }
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }

        return headers;
    }

    private void addHeadersIntoConnection(HttpURLConnection connection, Map<String, String> headers) {
        for (String key : headers.keySet()) {
            connection.setRequestProperty(key, headers.get(key));
        }
    }

    private byte[] toByteArray(Map<String, String> body) {
        String json = GsonHolder.getInstance().toJson(body, Map.class);
        return json.getBytes();
    }

    public class Response {

        private String content;
        private Header[] headers;

        public Response(String content, Header[] headers) {
            this.content = content;
            this.headers = headers;
        }

        public String getContent() {
            return content;
        }

        public Header[] getHeaders() {
            return headers;
        }
    }
}
