package cloud.fogbow.common.models;

import java.util.HashMap;

public class CloudStackUser extends CloudUser {
    private String domain;
    private HashMap<String, String> cookieHeaders;

    public CloudStackUser(String userId, String userName, String tokenValue, String domain, HashMap<String, String> cookieHeaders) {
        super(userId, userName, tokenValue);
        this.domain = domain;
        this.cookieHeaders = cookieHeaders;
    }

    public String getDomain() {
        return domain;
    }

    public HashMap<String, String> getCookieHeaders() {
        return cookieHeaders;
    }

    public void setCookieHeaders(HashMap<String, String> cookieHeaders) {
        this.cookieHeaders = cookieHeaders;
    }
}
