package cloud.fogbow.common.util.connectivity;

import java.util.List;
import java.util.Map;

public class GenericRequestHttpResponse extends GenericRequestResponse {
    private final Map<String, List<String>> headers;
    private int httpCode;

    public GenericRequestHttpResponse(String content, int httpCode, Map<String, List<String>> headers) {
        super(content);
        this.httpCode = httpCode;
        this.headers = headers;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }
}
