package cloud.fogbow.common.models;

public class GoogleCloudUser extends CloudUser {

    private String projectId;

    public GoogleCloudUser(String userId, String userName, String token) {
        super(userId, userName, token);
    }
    public GoogleCloudUser(String userId, String userName, String token, String projectId) {
        super(userId, userName, token);
        this.projectId = projectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
