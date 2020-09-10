package cloud.fogbow.common.util.connectivity.cloud.azure;

import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.ConfigurationErrorException;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InternalServerErrorException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.AzureUser;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.rest.LogLevel;
import org.apache.log4j.Logger;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class AzureClientCacheManager {

    private static final Logger LOGGER = Logger.getLogger(AzureClientCacheManager.class);

    private static final long LIFE_TIME_IN_MINUTES = 30;

    private final static LoadingCache<AzureUser, Azure> loadingCache;

    static {
        loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(LIFE_TIME_IN_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<AzureUser, Azure>() {
                    @Override
                    public Azure load(AzureUser azureUser) throws Exception {
                        LOGGER.debug(Messages.Log.CREATING_AZURE_CLIENT);
                        return createAzure(azureUser);
                    }
                });
    }

    @NotNull
    public static Azure getAzure(AzureUser azureUser) throws UnauthenticatedUserException {
        try {
            return loadingCache.get(azureUser);
        } catch (Exception e) {
            throw new UnauthenticatedUserException(e.getMessage());
        }
    }

    @NotNull
    @VisibleForTesting
    static Azure createAzure(AzureUser azureUser) throws FogbowException {
        try {
            AzureTokenCredentials azureTokenCredentials = buildAzureTokenCredentials(azureUser);
            return Azure.configure()
                    .withLogLevel(LogLevel.BASIC)
                    .authenticate(azureTokenCredentials)
                    .withDefaultSubscription();
        } catch (IOException | Error e) {
            throw new InternalServerErrorException(Messages.Exception.ERROR_WHILE_CREATING_AZURE_CLIENT);
        }
    }

    @VisibleForTesting
    static AzureTokenCredentials buildAzureTokenCredentials(AzureUser azureUser) {
        String clientId = azureUser.getClientId();
        String tenantId = azureUser.getTenantId();
        String clientKey = azureUser.getClientKey();
        String subscriptionId = azureUser.getSubscriptionId();

        final String managementEndpoint = AzureEnvironment.AZURE.managementEndpoint();
        final String activeDirectoryEndpoint = AzureEnvironment.AZURE.activeDirectoryEndpoint();
        final String resourceManagerEndpoint = AzureEnvironment.AZURE.resourceManagerEndpoint();
        final String graphEndpoint = AzureEnvironment.AZURE.graphEndpoint();
        final String keyVaultDnsSuffix = AzureEnvironment.AZURE.keyVaultDnsSuffix();
        AzureEnvironment azureEnvironment = getAzureEnvironment(managementEndpoint,
                activeDirectoryEndpoint, resourceManagerEndpoint, graphEndpoint, keyVaultDnsSuffix);
        return getApplicationTokenCredentials(clientId, tenantId, clientKey, azureEnvironment)
                .withDefaultSubscriptionId(subscriptionId);
    }

    @VisibleForTesting
    static AzureEnvironment getAzureEnvironment(String managementEndpoint, String activeDirectoryEndpoint,
                                                String resourceManagerEndpoint, String graphEndpoint,
                                                String keyVaultDnsSuffix) {

        return new AzureEnvironment(new HashMap<String, String>() {
            {
                this.put(AzureEnvironment.Endpoint.ACTIVE_DIRECTORY.toString(), activeDirectoryEndpoint.endsWith("/") ? activeDirectoryEndpoint : activeDirectoryEndpoint + "/");
                this.put(AzureEnvironment.Endpoint.MANAGEMENT.toString(), managementEndpoint);
                this.put(AzureEnvironment.Endpoint.RESOURCE_MANAGER.toString(), resourceManagerEndpoint);
                this.put(AzureEnvironment.Endpoint.GRAPH.toString(), graphEndpoint);
                this.put(AzureEnvironment.Endpoint.KEYVAULT.toString(), keyVaultDnsSuffix);
            }
        });
    }

    @VisibleForTesting
    static ApplicationTokenCredentials getApplicationTokenCredentials(String clientId, String tenantId,
                                                                      String clientKey, AzureEnvironment azureEnvironment) {

        return new ApplicationTokenCredentials(clientId, tenantId, clientKey, azureEnvironment);
    }

}
