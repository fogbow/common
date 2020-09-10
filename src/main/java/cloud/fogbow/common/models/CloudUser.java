package cloud.fogbow.common.models;

public class CloudUser extends User {
    private String token;

    public CloudUser(String userId, String userName, String token) {
        super(userId, userName);
        this.token = token;
    }
    
    public CloudUser(String userId, String userName) {
        super(userId, userName);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
