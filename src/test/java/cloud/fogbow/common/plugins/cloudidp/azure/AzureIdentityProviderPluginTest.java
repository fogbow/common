package cloud.fogbow.common.plugins.cloudidp.azure;

import cloud.fogbow.common.constants.AzureConstants;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.AzureUser;
import cloud.fogbow.common.util.AzureClientCacheManager;
import com.microsoft.azure.management.Azure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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

    // test case: When calling the checkCredentials method with all parameters right,
    // it must verify if it does not throw an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsSuccessfully() throws InvalidParameterException {
        // set up
        String regionName = "regionName";
        String resourceGroupName = "resourceGroupName";
        String clientKey = "clientKey";
        String clientId = "clientId";
        String subscriptionId = "subscriptionId";
        String tenantId = "tenantId";

        // exercise
        this.azureIdentityProviderPlugin.checkCredentials(
                subscriptionId, clientId, clientKey, tenantId, resourceGroupName, regionName);
    }

    // test case: When calling the checkCredentials method with parameter regionName null,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenRegionNameIsNull() throws InvalidParameterException {
        // set up
        String regionName = null;
        String resourceGroupName = "resourceGroupName";
        String clientKey = "clientKey";
        String clientId = "clientId";
        String subscriptionId = "subscriptionId";
        String tenantId = "tenantId";

        assertCredentials(regionName, resourceGroupName, clientKey, clientId, subscriptionId, tenantId);
    }

    // test case: When calling the checkCredentials method with parameter regionName empty,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenRegionNameIsEmpty() throws InvalidParameterException {
        // set up
        String regionName = "";
        String resourceGroupName = "resourceGroupName";
        String clientKey = "clientKey";
        String clientId = "clientId";
        String subscriptionId = "subscriptionId";
        String tenantId = "tenantId";

        assertCredentials(regionName, resourceGroupName, clientKey, clientId, subscriptionId, tenantId);
    }

    // test case: When calling the checkCredentials method with parameter resourceGroupName null,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenResourceGroupNameIsNull() throws InvalidParameterException {
        // set up
        String regionName = "regionName";
        String resourceGroupName = null;
        String clientKey = "clientKey";
        String clientId = "clientId";
        String subscriptionId = "subscriptionId";
        String tenantId = "tenantId";

        assertCredentials(regionName, resourceGroupName, clientKey, clientId, subscriptionId, tenantId);
    }

    // test case: When calling the checkCredentials method with parameter resourceGroupName empty,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenResourceGroupNameIsEmpty() throws InvalidParameterException {
        // set up
        String regionName = "regionName";
        String resourceGroupName = "";
        String clientKey = "clientKey";
        String clientId = "clientId";
        String subscriptionId = "subscriptionId";
        String tenantId = "tenantId";

        assertCredentials(regionName, resourceGroupName, clientKey, clientId, subscriptionId, tenantId);
    }

    // test case: When calling the checkCredentials method with parameter clientKey null,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenClientKeyIsNull() throws InvalidParameterException {
        // set up
        String regionName = "regionName";
        String resourceGroupName = "resourceGroupName";
        String clientKey = null;
        String clientId = "clientId";
        String subscriptionId = "subscriptionId";
        String tenantId = "tenantId";

        assertCredentials(regionName, resourceGroupName, clientKey, clientId, subscriptionId, tenantId);
    }

    // test case: When calling the checkCredentials method with parameter clientKey empty,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenClientKeyIsEmpty() throws InvalidParameterException {
        // set up
        String regionName = "regionName";
        String resourceGroupName = "resourceGroupName";
        String clientKey = "";
        String clientId = "clientId";
        String subscriptionId = "subscriptionId";
        String tenantId = "tenantId";

        assertCredentials(regionName, resourceGroupName, clientKey, clientId, subscriptionId, tenantId);
    }

    // test case: When calling the checkCredentials method with parameter clientId null,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenClientIdIsNull() throws InvalidParameterException {
        // set up
        String regionName = "regionName";
        String resourceGroupName = "resourceGroupName";
        String clientKey = "clientKey";
        String clientId = null;
        String subscriptionId = "subscriptionId";
        String tenantId = "tenantId";

        assertCredentials(regionName, resourceGroupName, clientKey, clientId, subscriptionId, tenantId);
    }

    // test case: When calling the checkCredentials method with parameter clientId empty,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenClientIdIsEmpty() throws InvalidParameterException {
        // set up
        String regionName = "regionName";
        String resourceGroupName = "resourceGroupName";
        String clientKey = "clientKey";
        String clientId = "";
        String subscriptionId = "subscriptionId";
        String tenantId = "tenantId";

        assertCredentials(regionName, resourceGroupName, clientKey, clientId, subscriptionId, tenantId);
    }

    // test case: When calling the checkCredentials method with parameter subscriptionId null,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenSubscriptionIdIsNull() throws InvalidParameterException {
        // set up
        String regionName = "regionName";
        String resourceGroupName = "resourceGroupName";
        String clientKey = "clientKey";
        String clientId = "clientId";
        String subscriptionId = null;
        String tenantId = "tenantId";

        assertCredentials(regionName, resourceGroupName, clientKey, clientId, subscriptionId, tenantId);
    }

    // test case: When calling the checkCredentials method with parameter subscriptionId empty,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenSubscriptionIdIsEmpty() throws InvalidParameterException {
        // set up
        String regionName = "regionName";
        String resourceGroupName = "resourceGroupName";
        String clientKey = "clientKey";
        String clientId = "clientId";
        String subscriptionId = "";
        String tenantId = "tenantId";

        assertCredentials(regionName, resourceGroupName, clientKey, clientId, subscriptionId, tenantId);
    }

    // test case: When calling the checkCredentials method with parameter tenantId null,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenTenantIdIsNull() throws InvalidParameterException {
        // set up
        String regionName = "regionName";
        String resourceGroupName = "resourceGroupName";
        String clientKey = "clientKey";
        String clientId = "clientId";
        String subscriptionId = "subscriptionId";
        String tenantId = null;

        assertCredentials(regionName, resourceGroupName, clientKey, clientId, subscriptionId, tenantId);
    }

    // test case: When calling the checkCredentials method with parameter tenantId empty,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenTenantIdIdIsEmpty() throws InvalidParameterException {
        // set up
        String regionName = "regionName";
        String resourceGroupName = "resourceGroupName";
        String clientKey = "clientKey";
        String clientId = "clientId";
        String subscriptionId = "subscriptionId";
        String tenantId = "";

        assertCredentials(regionName, resourceGroupName, clientKey, clientId, subscriptionId, tenantId);
    }

    private void assertCredentials(String regionName, String resourceGroupName, String clientKey, String clientId, String subscriptionId, String tenantId) throws InvalidParameterException {
        // verify
        this.expectedException.expect(InvalidParameterException.class);
        this.expectedException.expectMessage(Messages.Exception.NO_USER_CREDENTIALS);

        // exercise
        this.azureIdentityProviderPlugin.checkCredentials(
                subscriptionId, clientId, clientKey, tenantId, resourceGroupName, regionName);
    }

}
