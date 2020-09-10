package cloud.fogbow.common.util.connectivity;

import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.InternalServerErrorException;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest implements FogbowGenericRequest, Cloneable {

    private HttpMethod method;
    private String url;
    private Map<String, String> headers;
    private Map<String, String> body;

    public HttpRequest(HttpMethod method, String url, Map<String, String> body, Map<String, String> headers)
            throws InternalServerErrorException {
        if (headers == null || body == null) {
            throw new InternalServerErrorException(Messages.Exception.NEITHER_BODY_OR_HEADERS_CAN_BE_NULL);
        }

        this.method = method;
        this.url = url;
        this.headers = headers;
        this.body = body;
    }


    public HttpRequest(HttpMethod method, String url, Map<String, String> body) throws InternalServerErrorException {
        this(method, url, body, new HashMap<>());
    }

    public HttpRequest(HttpMethod method, String url) throws InternalServerErrorException {
        this(method, url, new HashMap<>(), new HashMap<>());
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }
}
