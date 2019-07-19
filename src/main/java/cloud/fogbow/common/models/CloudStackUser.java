package cloud.fogbow.common.models;

import java.util.Map;

public class CloudStackUser extends CloudUser {
    private String domain;
    private Map<String, String> cookieHeaders;

    public CloudStackUser(String userId, String userName, String tokenValue, String domain, Map<String, String> cookieHeaders) {
        super(userId, userName, tokenValue);
        this.domain = domain;
        this.cookieHeaders = cookieHeaders;
    }

    public String getDomain() {
        return domain;
    }

    public Map<String, String> getCookieHeaders() {
        return cookieHeaders;
    }

}
