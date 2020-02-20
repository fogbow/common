package cloud.fogbow.common.plugins.cloudidp.azure;

import cloud.fogbow.common.constants.AzureConstants;
import static cloud.fogbow.common.constants.AzureConstants.Credential.*;
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
        checkCredentials(userCredentials);
        String subscriptionId = getCredential(userCredentials, SUBSCRIPTION_ID_KEY);
        String clientId = getCredential(userCredentials, CLIENT_ID_KEY);
        String clientKey = getCredential(userCredentials, CLIENT_KEY);
        String tenantId = getCredential(userCredentials, TENANT_ID_KEY);
        String resourceGroupName = getCredential(userCredentials, RESOURCE_GROUP_NAME_KEY);
        String regionName = getCredential(userCredentials, REGION_NAME_KEY);

        String userId = clientId;
        String userName = clientId;
        AzureUser azureUser = new AzureUser(userId, userName, clientId, tenantId,
                clientKey, subscriptionId, resourceGroupName, regionName);
        return authenticate(azureUser);
    }

    private String getCredential(@NotNull Map<String, String> userCredentials, @NotNull AzureConstants.Credential credential) {
        return userCredentials.get(credential.getValue());
    }

    @VisibleForTesting
    AzureUser authenticate(AzureUser azureUser) throws UnauthenticatedUserException {
        checkAzureClient(azureUser);
        return azureUser;
    }

    @VisibleForTesting
    void checkCredentials(Map<String, String> userCredentials)
            throws InvalidParameterException {

        String value;
        for (AzureConstants.Credential credential : AzureConstants.Credential.values()) {
            value = userCredentials.get(credential.getValue());
            if (value == null || value.isEmpty()) {
                throw new InvalidParameterException(String.format(Messages.Exception.NO_USER_CREDENTIAL_S, credential.getValue()));
            }
        }
    }

    private void checkAzureClient(AzureUser azureUser) throws UnauthenticatedUserException {
        try {
            AzureClientCacheManager.getAzure(azureUser);
        } catch (UnauthenticatedUserException e) {
            throw e;
        }
    }

}
