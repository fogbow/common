package cloud.fogbow.common.plugins.cloudidp.azure.v1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.annotations.VisibleForTesting;
import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.rest.LogLevel;

import cloud.fogbow.common.constants.AzureConstants;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.models.AzureV1User;
import cloud.fogbow.common.plugins.cloudidp.CloudIdentityProviderPlugin;

public class AzureV1IdentityProviderPlugin implements CloudIdentityProviderPlugin<AzureV1User> {

    @Override
    public AzureV1User getCloudUser(@NotNull Map<String, String> userCredentials) throws FogbowException {
        String subscriptionId = userCredentials.get(AzureConstants.SUBSCRIPTION_KEY);
        String clientId = userCredentials.get(AzureConstants.CLIENT_KEY);
        String accessKeyId = userCredentials.get(AzureConstants.ACCESS_KEY);
        String tenantId = userCredentials.get(AzureConstants.TENANT_KEY);

        checkCredentials(subscriptionId, clientId, accessKeyId, tenantId);
        try {
            return authenticate(subscriptionId, clientId, accessKeyId, tenantId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @VisibleForTesting
    AzureV1User authenticate(
            @NotBlank String subscriptionId,
            @NotBlank String clientId,
            @NotBlank String accessKeyId,
            @NotBlank String tenantId) throws IOException {

        

        AzureEnvironment azureEnvironment = getAzureEnvironment();

        AzureTokenCredentials azureTokenCredentials =
                getApplicationTokenCredentials(clientId, tenantId, clientId, azureEnvironment)
                .withDefaultSubscriptionId(subscriptionId);

        Azure azure = Azure.configure()
                .withLogLevel(LogLevel.BASIC)
                .authenticate(azureTokenCredentials)
                .withDefaultSubscription();

        String resourceGroup = azure.resourceGroups().toString();
        System.out.println(resourceGroup);

        return new AzureV1User(null, null, null);
    }

    @VisibleForTesting
    static ApplicationTokenCredentials getApplicationTokenCredentials(
            @NotBlank String clientId,
            @NotBlank String tenantId,
            @NotBlank String clientKey,
            @NotNull AzureEnvironment azureEnvironment) {

        return new ApplicationTokenCredentials(clientId, tenantId, clientKey, azureEnvironment);
    }

    @VisibleForTesting
    static AzureEnvironment getAzureEnvironment() {
        String activeDirectoryEndpoint = AzureEnvironment.AZURE.activeDirectoryEndpoint();
        return new AzureEnvironment(new HashMap<String, String>() {
            {
                this.put(AzureEnvironment.Endpoint.ACTIVE_DIRECTORY.toString(),
                        activeDirectoryEndpoint.endsWith(AzureConstants.URL_SEPARATOR) ? activeDirectoryEndpoint
                                : activeDirectoryEndpoint + AzureConstants.URL_SEPARATOR);
                this.put(AzureEnvironment.Endpoint.MANAGEMENT.toString(), AzureEnvironment.AZURE.managementEndpoint());
                this.put(AzureEnvironment.Endpoint.RESOURCE_MANAGER.toString(), AzureEnvironment.AZURE.resourceManagerEndpoint());
                this.put(AzureEnvironment.Endpoint.GRAPH.toString(), AzureEnvironment.AZURE.graphEndpoint());
                this.put(AzureEnvironment.Endpoint.KEYVAULT.toString(), AzureEnvironment.AZURE.keyVaultDnsSuffix());
            }
        });
    }

    @VisibleForTesting
    void checkCredentials(
            @NotBlank String subscriptionId,
            @NotBlank String clientId,
            @NotBlank String accessKeyId,
            @NotBlank String tenantId) throws InvalidParameterException {

        if(subscriptionId == null || clientId == null || accessKeyId == null || tenantId == null) {
            throw new InvalidParameterException(Messages.Exception.NO_USER_CREDENTIALS);
        }
    }

}
