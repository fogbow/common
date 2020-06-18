package cloud.fogbow.common.plugins.cloudidp.opennebula.v5_4;

import cloud.fogbow.common.constants.OpenNebulaConstants;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InternalServerErrorException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.OpenNebulaUser;
import cloud.fogbow.common.util.connectivity.cloud.opennebula.OpenNebulaClientUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.user.UserPool;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OpenNebulaClientUtil.class})
public class OpenNebulaIdentityProviderPluginTest {

    private OpenNebulaIdentityProviderPlugin plugin;
    public static final String FAKE_IDENTITY_URL = "http://localhost:0000";
    public static final String FAKE_USER_ID = "fake-id";
    public static final String FAKE_USER_PASSWORD = "fake-password";
    public static final String INVALID_PASSWORD = "invalid-password";
    public static final String ONE_RESPONSE_MESSAGE = "Random oneResponse return message";

    @Before
    public void setup() throws InternalServerErrorException {
        this.plugin = Mockito.spy(new OpenNebulaIdentityProviderPlugin(FAKE_IDENTITY_URL));

        Client client = Mockito.mock(Client.class);

        PowerMockito.mockStatic(OpenNebulaClientUtil.class);
        BDDMockito.given(OpenNebulaClientUtil.createClient(Mockito.anyString(), Mockito.anyString())).willReturn(client);
    }

    // Test case: When the right credentials are provided and the user is
    // authenticated. This tests mocks the isAuthenticated. Below, there's a
    // test that dowst not mock this method.
    @Test
    public void testAuthenticateSuccess() throws FogbowException {
        // setup
        Mockito.doReturn(true).when(this.plugin).isAuthenticated(Mockito.anyString());
        Map<String,String> credentials = getCredentials(FAKE_USER_ID, FAKE_USER_PASSWORD);

        // exercise
        OpenNebulaUser openNebulaUser = this.plugin.getCloudUser(credentials);

        // verify
        Assert.assertNotNull(openNebulaUser);
        Assert.assertEquals(FAKE_USER_ID, openNebulaUser.getId());
        Mockito.verify(this.plugin).isAuthenticated(Mockito.anyString());
    }

    // Test case: When a credential map is properly formatted but
    // The credentials are not recognized by the cloud.
    @Test(expected = UnauthenticatedUserException.class)
    public void testAuthenticateFail() throws FogbowException {
        Mockito.doReturn(false).when(this.plugin).isAuthenticated(Mockito.anyString());
        Map<String,String> credentials = getCredentials(FAKE_USER_ID, INVALID_PASSWORD);

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // Test case: When an null password is provided
    @Test(expected = UnauthenticatedUserException.class)
    public void testAuthenticateWithNullParams() throws FogbowException {
        Map<String,String> credentials = getCredentials(FAKE_USER_ID, null);

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // Test case: When an empty password is provided
    @Test(expected = UnauthenticatedUserException.class)
    public void testAuthenticateWithEmptyParams() throws FogbowException {
        Map<String,String> credentials = getCredentials(FAKE_USER_ID, "");

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // Test case: When invoking the getCloudUser method with a valid credential map,
    // and the Opennebula's cloud return appropriate message.
    @Test
    public void testIsAuthenticatedSuccessWithoutMockingHelperMethod() throws FogbowException {
        // setup
        Map<String,String> credentials = getCredentials(FAKE_USER_ID, FAKE_USER_PASSWORD);
        UserPool userPool = Mockito.mock(UserPool.class);

        boolean RESPONSE_IS_SUCCESS = true;
        OneResponse mockedInfo = new OneResponse(RESPONSE_IS_SUCCESS, ONE_RESPONSE_MESSAGE);
        Mockito.when(userPool.info()).thenReturn(mockedInfo);
        BDDMockito.given(OpenNebulaClientUtil.getUserPool(Mockito.any())).willReturn(userPool);

        // exercise
        OpenNebulaUser openNebulaUser = this.plugin.getCloudUser(credentials);

        // verify
        Assert.assertNotNull(openNebulaUser);
        Assert.assertEquals(FAKE_USER_ID, openNebulaUser.getId());
        Mockito.verify(this.plugin).isAuthenticated(Mockito.anyString());
    }

    private Map<String, String> getCredentials(String name, String password) {
        Map<String, String> userCredentials = new HashMap();
        userCredentials.put(OpenNebulaConstants.USERNAME, name);
        userCredentials.put(OpenNebulaConstants.PASSWORD, password);
        return userCredentials;
    }
}
