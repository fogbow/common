package cloud.fogbow.common.models;

public class CloudToken {
    private String tokenProviderId;
    private String userId;
    private String tokenValue;

    public CloudToken(String tokenProviderId, String userId, String tokenValue) {
        this.tokenProviderId = tokenProviderId;
        this.userId = userId;
        this.tokenValue = tokenValue;
    }

    public String getTokenProviderId() {
        return tokenProviderId;
    }

    public void setTokenProviderId(String tokenProviderId) {
        this.tokenProviderId = tokenProviderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }
}
