package cloud.fogbow.common.plugins.cloudidp.azure;

import cloud.fogbow.common.constants.AzureConstants;
import static cloud.fogbow.common.constants.AzureConstants.Credential.*;
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

    private static final String DEFAULT_CLIENT_ID = "client-id";
    private static final String DEFAULT_TENANT_ID = "tenant-id";
    private static final String DEFAULT_CLIENT_KEY = "client-key";
    private static final String DEFAULT_SUBSCRIPTION_ID = "subscription-id";

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
        Map<String, String> credentials = createCredentials();
        AzureUser azureUserExpected = createUser();

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
        Map<String, String> credentials = createCredentials();
        AzureUser azureUserExpected = createUser();

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
        Map<String, String> userCredentials = createCredentials();

        // exercise
        this.azureIdentityProviderPlugin.checkCredentials(userCredentials);
    }

    // test case: When calling the checkCredentials method with parameter clientKey null,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenClientKeyIsNull() throws InvalidParameterException {
        // set up
        String clientKey = null;
        assertCredentials(clientKey, CLIENT_KEY);
    }

    // test case: When calling the checkCredentials method with parameter clientKey empty,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenClientKeyIsEmpty() throws InvalidParameterException {
        // set up
        String clientKey = "";
        assertCredentials(clientKey, CLIENT_KEY);
    }

    // test case: When calling the checkCredentials method with parameter clientId null,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenClientIdIsNull() throws InvalidParameterException {
        // set up
        String clientId = null;
        assertCredentials(clientId, CLIENT_ID_KEY);
    }

    // test case: When calling the checkCredentials method with parameter clientId empty,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenClientIdIsEmpty() throws InvalidParameterException {
        // set up
        String clientId = "";
        assertCredentials(clientId, CLIENT_ID_KEY);
    }

    // test case: When calling the checkCredentials method with parameter subscriptionId null,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenSubscriptionIdIsNull() throws InvalidParameterException {
        // set up
        String subscriptionId = null;
        assertCredentials(subscriptionId, SUBSCRIPTION_ID_KEY);
    }

    // test case: When calling the checkCredentials method with parameter subscriptionId empty,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenSubscriptionIdIsEmpty() throws InvalidParameterException {
        // set up
        String subscriptionId = "";
        assertCredentials(subscriptionId, SUBSCRIPTION_ID_KEY);
    }

    // test case: When calling the checkCredentials method with parameter tenantId null,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenTenantIdIsNull() throws InvalidParameterException {
        // set up
        String tenantId = null;
        assertCredentials(tenantId, TENANT_ID_KEY);
    }

    // test case: When calling the checkCredentials method with parameter tenantId empty,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testCheckCredentialsFailWhenTenantIdIdIsEmpty() throws InvalidParameterException {
        // set up
        String tenantId = "";
        assertCredentials(tenantId, TENANT_ID_KEY);
    }

    private void assertCredentials(String value, AzureConstants.Credential credential) throws InvalidParameterException {
        // set up
        Map<String, String> credentials = createCredentials();
        credentials.put(credential.getValue(), value);

        // verify
        this.expectedException.expect(InvalidParameterException.class);
        this.expectedException.expectMessage(Messages.Exception.NO_USER_CREDENTIALS);

        // exercise
        this.azureIdentityProviderPlugin.checkCredentials(credentials);
    }


    private Map<String, String> createCredentials(String clientKey, String clientId, String subscriptionId, String tenantId) {
        Map<String, String> credentials = new HashMap<>();
        credentials.put(CLIENT_KEY.getValue(), clientKey);
        credentials.put(CLIENT_ID_KEY.getValue(), clientId);
        credentials.put(SUBSCRIPTION_ID_KEY.getValue(), subscriptionId);
        credentials.put(TENANT_ID_KEY.getValue(), tenantId);
        return credentials;
    }

    private Map<String, String> createCredentials() {
        return createCredentials(DEFAULT_CLIENT_KEY, DEFAULT_CLIENT_ID,
                DEFAULT_SUBSCRIPTION_ID, DEFAULT_TENANT_ID);
    }

    private AzureUser createUser() {
        return new AzureUser(DEFAULT_CLIENT_ID, DEFAULT_CLIENT_ID, DEFAULT_CLIENT_ID, DEFAULT_TENANT_ID,
                DEFAULT_CLIENT_KEY, DEFAULT_SUBSCRIPTION_ID);
    }
}
