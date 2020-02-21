package cloud.fogbow.common.models;

import cloud.fogbow.common.constants.AzureConstants;

import java.util.Objects;

public class AzureUser extends CloudUser {

    private String clientId;
    private String tenantId;
    private String clientKey;
    private String subscriptionId;

    public AzureUser(String userId, String userName) {
        super(userId, userName);
    }
    
    public AzureUser(String userId, String userName, String clientId, String tenantId, String clientKey, String subscriptionId) {
        super(userId, userName);
        this.clientId = clientId;
        this.tenantId = tenantId;
        this.clientKey = clientKey;
        this.subscriptionId = subscriptionId;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public String getClientKey() {
        return this.clientKey;
    }

    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AzureUser azureUser = (AzureUser) o;
        return clientId.equals(azureUser.clientId) &&
                tenantId.equals(azureUser.tenantId) &&
                clientKey.equals(azureUser.clientKey) &&
                subscriptionId.equals(azureUser.subscriptionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, tenantId, clientKey, subscriptionId);
    }

}
