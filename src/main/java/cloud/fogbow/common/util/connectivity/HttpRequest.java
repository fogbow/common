package cloud.fogbow.common.util.connectivity;

import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.constants.Messages;

import java.util.HashMap;

import static cloud.fogbow.common.constants.Messages.Exception.NEITHER_BODY_OR_HEADERS_CAN_BE_NULL;

public class HttpRequest implements FogbowGenericRequest, Cloneable {

    private HttpMethod method;
    private String url;
    private HashMap<String, String> headers;
    private HashMap<String, String> body;

    public HttpRequest(HttpMethod method, String url, HashMap<String, String> body, HashMap<String, String> headers) {
        if (headers == null || body == null) {
            throw new IllegalArgumentException(NEITHER_BODY_OR_HEADERS_CAN_BE_NULL);
        }

        this.method = method;
        this.url = url;
        this.headers = headers;
        this.body = body;
    }


    public HttpRequest(HttpMethod method, String url, HashMap<String, String> body) {
        this(method, url, body, new HashMap<>());
    }

    public HttpRequest(HttpMethod method, String url) {
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

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public HashMap<String, String> getBody() {
        return body;
    }

    public void setBody(HashMap<String, String> body) {
        this.body = body;
    }

    @Override
    public Object clone() {
        try {
            HttpRequest cloned = (HttpRequest) super.clone();
            cloned.headers = (HashMap<String, String>) this.headers.clone();
            cloned.body = (HashMap<String, String>) this.body.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(String.format(Messages.Exception.CLASS_S_SHOULD_BE_CLONEABLE, this.getClass().getName()));
        }
    }
}