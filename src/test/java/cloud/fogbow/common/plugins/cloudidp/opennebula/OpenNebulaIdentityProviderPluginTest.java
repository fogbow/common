package cloud.fogbow.common.plugins.cloudidp.opennebula;

import cloud.fogbow.common.constants.OpenNebulaConstants;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.OpenNebulaUser;
import cloud.fogbow.common.util.connectivity.cloud.opennebula.OpenNebulaClientUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.OneResponse;
import org.opennebula.client.user.UserPool;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OpenNebulaClientUtil.class})
public class OpenNebulaIdentityProviderPluginTest {

    private OpenNebulaIdentityProviderPlugin plugin;
    public static final String FAKE_IDENTITY_URL = "http://localhost:0000";
    public static final String FAKE_USER_ID = "fake-id";
    public static final String FAKE_USER_PASSWORD = "fake-password";
    public static final String INVALID_PASSWORD = "invalid-password";
    public static final String ONE_RESPONSE_MESSAGE = "Random oneResponse return message";

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setup() throws UnexpectedException {
        this.plugin = Mockito.spy(new OpenNebulaIdentityProviderPlugin(FAKE_IDENTITY_URL));

        Client client = Mockito.mock(Client.class);

        PowerMockito.mockStatic(OpenNebulaClientUtil.class);
        BDDMockito.given(OpenNebulaClientUtil.createClient(Mockito.anyString(), Mockito.anyString())).willReturn(client);
    }

    @Test
    public void testAuthenticateSuccess() throws FogbowException {
        // setup
        Mockito.doReturn(true).when(this.plugin).isAuthenticated(Mockito.anyString());
        HashMap<String,String> credentials = getCredentials(FAKE_USER_ID, FAKE_USER_PASSWORD);

        // exercise
        OpenNebulaUser openNebulaUser = this.plugin.getCloudUser(credentials);

        // verify
        Assert.assertNotNull(openNebulaUser);
        Assert.assertEquals(FAKE_USER_ID, openNebulaUser.getId());
        Mockito.verify(this.plugin).isAuthenticated(Mockito.anyString());
    }

    @Test(expected = UnauthenticatedUserException.class)
    public void testAuthenticateUnsuccess() throws FogbowException {
        Mockito.doReturn(false).when(this.plugin).isAuthenticated(Mockito.anyString());
        HashMap<String,String> credentials = getCredentials(FAKE_USER_ID, INVALID_PASSWORD);

        // exercise
        OpenNebulaUser openNebulaUser = this.plugin.getCloudUser(credentials);
    }

    @Test(expected = InvalidParameterException.class)
    public void testAuthenticateWithNullParams() throws FogbowException {
        HashMap<String,String> credentials = getCredentials(FAKE_USER_ID, null);

        // exercise
        OpenNebulaUser openNebulaUser = this.plugin.getCloudUser(credentials);
    }

    @Test(expected = InvalidParameterException.class)
    public void testAuthenticateWithEmptyParams() throws FogbowException {
        HashMap<String,String> credentials = getCredentials(FAKE_USER_ID, "");

        // exercise
        OpenNebulaUser openNebulaUser = this.plugin.getCloudUser(credentials);
    }

    @Test
    public void testIsAuthenticatedSuccessWithoutMockingHelperMethod() throws FogbowException {
        // setup
        HashMap<String,String> credentials = getCredentials(FAKE_USER_ID, FAKE_USER_PASSWORD);

        UserPool userPool = new UserPool();
        BDDMockito.given(OpenNebulaClientUtil.getUserPool(Mockito.anyObject())).willReturn()

        // exercise
        OpenNebulaUser openNebulaUser = this.plugin.getCloudUser(credentials);

        // verify
        Assert.assertNotNull(openNebulaUser);
        Assert.assertEquals(FAKE_USER_ID, openNebulaUser.getId());
//        PowerMockito.verifyStatic(OpenNebulaClientUtil.class);

//        Mockito.verifyS(OpenNebulaClientUtil)

    }

    private HashMap<String, String> getCredentials(String name, String password) {
        HashMap<String, String> userCredentials = new HashMap<String, String>();
        userCredentials.put(OpenNebulaConstants.USERNAME, name);
        userCredentials.put(OpenNebulaConstants.PASSWORD, password);
        return userCredentials;
    }

    private void getMockedOpenNebulaClient(boolean isErrorReturn) throws ClientConfigurationException {
        OneResponse response = Mockito.spy(new OneResponse(isErrorReturn, ONE_RESPONSE_MESSAGE));
        Client client = new Client();
        UserPool userPool = new UserPool(client);
    }
}
