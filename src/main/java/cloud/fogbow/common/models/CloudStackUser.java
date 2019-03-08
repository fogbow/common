package cloud.fogbow.common.models;

import java.util.HashMap;

public class CloudStackUser extends CloudUser {
    private HashMap<String, String> cookieHeader;

    public CloudStackUser(String userId, String userName, String tokenValue, HashMap<String, String> cookieHeader) {
        super(userId, userName, tokenValue);
        this.cookieHeader = cookieHeader;
    }

    public HashMap<String, String> getCookieHeader() {
        return cookieHeader;
    }

    public void setCookieHeader(HashMap<String, String> cookieHeader) {
        this.cookieHeader = cookieHeader;
    }
}
