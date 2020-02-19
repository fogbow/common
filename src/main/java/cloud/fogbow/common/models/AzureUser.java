package cloud.fogbow.common.models;

import cloud.fogbow.common.constants.AzureConstants;

import java.util.Objects;

public class AzureUser extends CloudUser {

    private String clientId;
    private String tenantId;
    private String clientKey;
    private String subscriptionId;
    private String resourceGroupName;
    private String regionName;

    public AzureUser(String userId, String userName) {
        super(userId, userName, AzureConstants.NON_TOKEN);
    }
    
    public AzureUser(String userId, String userName, String clientId, String tenantId, String clientKey, String subscriptionId, String resourceGroupName, String regionName) {
        super(userId, userName, AzureConstants.NON_TOKEN);
        this.clientId = clientId;
        this.tenantId = tenantId;
        this.clientKey = clientKey;
        this.subscriptionId = subscriptionId;
        this.resourceGroupName = resourceGroupName;
        this.regionName = regionName;
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

    public String getResourceGroupName() {
        return this.resourceGroupName;
    }

    public String getRegionName() {
        return this.regionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AzureUser azureUser = (AzureUser) o;
        return clientId.equals(azureUser.clientId) &&
                tenantId.equals(azureUser.tenantId) &&
                clientKey.equals(azureUser.clientKey) &&
                subscriptionId.equals(azureUser.subscriptionId) &&
                resourceGroupName.equals(azureUser.resourceGroupName) &&
                regionName.equals(azureUser.regionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, tenantId, clientKey, subscriptionId, resourceGroupName, regionName);
    }

}
