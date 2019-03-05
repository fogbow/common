package cloud.fogbow.common.models;

public class OpenStackV3User extends CloudUser {
    private String projectId;

    public OpenStackV3User(String userId, String userName, String tokenValue, String projectId) {
        super(userId, userName, tokenValue);
        this.projectId = projectId;
    }

    public String getProjectId() {
        return this.projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
