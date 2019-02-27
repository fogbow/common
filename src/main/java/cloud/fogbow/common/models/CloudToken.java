package cloud.fogbow.common.models;

public class CloudToken {
    private String tokenProviderId;
    private String userId;
    private String userName;
    private String tokenValue;

    public CloudToken(FederationUser federationUser) {
        this.tokenProviderId = federationUser.getTokenProviderId();
        this.userId = federationUser.getUserId();
        this.userName = federationUser.getUserName();
        this.tokenValue = federationUser.getTokenValue();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
