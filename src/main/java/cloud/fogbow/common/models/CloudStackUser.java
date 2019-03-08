package cloud.fogbow.common.models;

import java.util.HashMap;

public class CloudStackUser extends CloudUser {
    private HashMap<String, String> cookieHeaders;

    public CloudStackUser(String userId, String userName, String tokenValue, HashMap<String, String> cookieHeaders) {
        super(userId, userName, tokenValue);
        this.cookieHeaders = cookieHeaders;
    }

    public HashMap<String, String> getCookieHeaders() {
        return cookieHeaders;
    }

    public void setCookieHeaders(HashMap<String, String> cookieHeaders) {
        this.cookieHeaders = cookieHeaders;
    }
}
