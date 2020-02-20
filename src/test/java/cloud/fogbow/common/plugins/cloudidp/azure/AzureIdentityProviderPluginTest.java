package cloud.fogbow.common.plugins.cloudidp.azure;

import cloud.fogbow.common.constants.AzureConstants;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.AzureUser;
import cloud.fogbow.common.util.AzureClientCacheManager;
import com.microsoft.azure.management.Azure;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AzureClientCacheManager.class})
public class AzureIdentityProviderPluginTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private AzureIdentityProviderPlugin azureIdentityProviderPlugin;
    private AzureUser azureUserDefault = new AzureUser("", "");

    @Before
    public void setUp() {
        this.azureIdentityProviderPlugin = Mockito.spy(new AzureIdentityProviderPlugin());
    }

    // test case: When calling the getCloudUser method with mocked methods,
    // it must verify if it returns right azureUser
    @Test
    public void testGetCloudUserSuccessfully() throws FogbowException {
        // set up
        Map<String, String> credentials = new HashMap<>();
        String regionName = "regionName";
        String resourceGroupName = "resourceGroupName";
        String clientKey = "clientKey";
        String clientId = "clientId";
        String subscriptionId = "subscriptionId";
        String tenantId = "tenantId";
        credentials.put(AzureConstants.REGION_NAME_KEY, regionName);
        credentials.put(AzureConstants.RESOURCE_GROUP_NAME_KEY, resourceGroupName);
        credentials.put(AzureConstants.CLIENT_KEY, clientKey);
        credentials.put(AzureConstants.CLIENT_ID_KEY, clientId);
        credentials.put(AzureConstants.SUBSCRIPTION_ID_KEY, subscriptionId);
        credentials.put(AzureConstants.TENANT_ID_KEY, tenantId);

        AzureUser azureUserExpected = new AzureUser(clientId, clientId, clientId, tenantId,
                clientKey, subscriptionId, resourceGroupName, regionName);

        Azure azure = null;
        PowerMockito.mockStatic(AzureClientCacheManager.class);
        PowerMockito.when(AzureClientCacheManager.getAzure(Mockito.eq(azureUserExpected)))
                .thenReturn(azure);

        // exercise
        AzureUser azureUser = this.azureIdentityProviderPlugin.getCloudUser(credentials);

        // verify
        Assert.assertEquals(azureUserExpected, azureUser);
    }

    // test case: When calling the getCloudUser method with mocked methods,
    // it must verify if it throws a FogbowException
    @Test
    public void testGetCloudUserFail() throws FogbowException {
        // set up
        Map<String, String> credentials = new HashMap<>();
        String regionName = "regionName";
        String resourceGroupName = "resourceGroupName";
        String clientKey = "clientKey";
        String clientId = "clientId";
        String subscriptionId = "subscriptionId";
        String tenantId = "tenantId";
        credentials.put(AzureConstants.REGION_NAME_KEY, regionName);
        credentials.put(AzureConstants.RESOURCE_GROUP_NAME_KEY, resourceGroupName);
        credentials.put(AzureConstants.CLIENT_KEY, clientKey);
        credentials.put(AzureConstants.CLIENT_ID_KEY, clientId);
        credentials.put(AzureConstants.SUBSCRIPTION_ID_KEY, subscriptionId);
        credentials.put(AzureConstants.TENANT_ID_KEY, tenantId);

        AzureUser azureUserExpected = new AzureUser(clientId, clientId, clientId, tenantId,
                clientKey, subscriptionId, resourceGroupName, regionName);

        PowerMockito.mockStatic(AzureClientCacheManager.class);
        PowerMockito.when(AzureClientCacheManager.getAzure(Mockito.eq(azureUserExpected)))
                .thenThrow(new UnauthenticatedUserException());

        // verify
        this.expectedException.expect(FogbowException.class);

        // exercise
        this.azureIdentityProviderPlugin.getCloudUser(credentials);
    }

    // test case: When calling the authenticate method with mocked methods,
    // it must verify if it returns the same azureUser
    @Test
    public void testAuthenticateSuccessfully() throws UnauthenticatedUserException {
        // set up
        Azure azure = null;
        PowerMockito.mockStatic(AzureClientCacheManager.class);
        PowerMockito.when(AzureClientCacheManager.getAzure(Mockito.eq(this.azureUserDefault)))
                .thenReturn(azure);

        // exercise
        AzureUser azureUser = this.azureIdentityProviderPlugin.authenticate(this.azureUserDefault);

        // verify
        Assert.assertEquals(this.azureUserDefault, azureUser);
    }

    // test case: When calling the authenticate method with mocked methods,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testAuthenticateFail() throws UnauthenticatedUserException {
        // set up
        PowerMockito.mockStatic(AzureClientCacheManager.class);
        PowerMockito.when(AzureClientCacheManager.getAzure(this.azureUserDefault))
                .thenThrow(new UnauthenticatedUserException());

        // verify
        this.expectedException.expect(UnauthenticatedUserException.class);

        // exercise
        this.azureIdentityProviderPlugin.authenticate(this.azureUserDefault);
    }

    // test case:
    @Ignore
    @Test
    public void testCheckCredentialsSuccessfully() {
        // set up
        // exercise
        // verify
    }

}
