package cloud.fogbow.common.plugins.cloudidp.azure;

import cloud.fogbow.common.constants.AzureConstants;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.AzureUser;
import cloud.fogbow.common.plugins.cloudidp.CloudIdentityProviderPlugin;
import cloud.fogbow.common.util.AzureClientCacheManager;
import com.google.common.annotations.VisibleForTesting;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class AzureIdentityProviderPlugin implements CloudIdentityProviderPlugin<AzureUser> {

    @Override
    public AzureUser getCloudUser(@NotNull Map<String, String> userCredentials) throws FogbowException {
        String subscriptionId = userCredentials.get(AzureConstants.SUBSCRIPTION_ID_KEY);
        String clientId = userCredentials.get(AzureConstants.CLIENT_ID_KEY);
        String clientKey = userCredentials.get(AzureConstants.CLIENT_KEY);
        String tenantId = userCredentials.get(AzureConstants.TENANT_ID_KEY);
        String resourceGroupName = userCredentials.get(AzureConstants.RESOURCE_GROUP_NAME_KEY);
        String regionName = userCredentials.get(AzureConstants.REGION_NAME_KEY);
        checkCredentials(subscriptionId, clientId, clientKey, tenantId, resourceGroupName, regionName);

        String userId = clientId;
        String userName = clientId;
        AzureUser azureUser = new AzureUser(userId, userName, clientId, tenantId,
                clientKey, subscriptionId, resourceGroupName, regionName);
        return authenticate(azureUser);
    }

    @VisibleForTesting
    AzureUser authenticate(AzureUser azureUser) throws UnauthenticatedUserException {
        checkAzureClient(azureUser);
        return azureUser;
    }

    private void checkAzureClient(AzureUser azureUser) throws UnauthenticatedUserException {
        try {
            AzureClientCacheManager.getAzure(azureUser);
        } catch (UnauthenticatedUserException e) {
            throw e;
        }
    }

    @VisibleForTesting
    void checkCredentials(String subscriptionId, String clientId, String accessKeyId,
                          String tenantId, String resourceGroupName, String regionName)
            throws InvalidParameterException {

        if(subscriptionId.isEmpty() || clientId.isEmpty() || accessKeyId.isEmpty()
                || tenantId.isEmpty() || resourceGroupName.isEmpty() || regionName.isEmpty()) {

            throw new InvalidParameterException(Messages.Exception.NO_USER_CREDENTIALS);
        }
    }

}
