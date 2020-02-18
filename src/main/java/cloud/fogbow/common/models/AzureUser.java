package cloud.fogbow.common.models;

import cloud.fogbow.common.constants.AzureConstants;

public class AzureUser extends CloudUser {

    private String clientId;
    private String tenantId;
    private String clientKey;
    private String subscriptionId;
    private String resourceGroupName;
    private String regionName;

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

}
