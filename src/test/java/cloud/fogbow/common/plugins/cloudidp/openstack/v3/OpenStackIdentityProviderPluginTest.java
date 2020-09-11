package cloud.fogbow.common.plugins.cloudidp.openstack.v3;

import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.constants.OpenStackConstants;
import cloud.fogbow.common.exceptions.InternalServerErrorException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.OpenStackV3User;
import cloud.fogbow.common.util.connectivity.HttpRequestClient;
import cloud.fogbow.common.util.connectivity.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpRequestClient.class})
public class OpenStackIdentityProviderPluginTest {

    private static final String ANY_VALUE = "anything";
    private static final String FAKE_URL = "http://localhost:0000";
    private OpenStackIdentityProviderPlugin plugin;

    @Before
    public void setup() {
        this.plugin = Mockito.spy(new OpenStackIdentityProviderPlugin(FAKE_URL));
    }

    // test case: When invoking the getCloudUser method with a null credential map,
    // it must throw an InternalServerErrorException.
    @Test(expected = NullPointerException.class) // verify
    public void testGetCloudUserWithNullCredentialsMap() throws FogbowException {
        // set up
        Map<String, String> credentials = null;

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // test case: When invoking the getCloudUser method with an empty credential
    // map, it must throw an UnauthenticatedUserException.
    @Test(expected = UnauthenticatedUserException.class) // verify
    public void testGetCloudUserWithEmptyCredentialsMap() throws FogbowException {
        // set up
        Map<String, String> credentials = new HashMap<String, String>();

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // test case: When invoking the getCloudUser method with a credential map
    // without a user name, it must throw an UnauthenticatedUserException.
    @Test(expected = UnauthenticatedUserException.class) // verify
    public void testGetCloudUserWithoutUsername() throws FogbowException {
        // set up
        Map<String, String> credentials = createCredentials(null, ANY_VALUE, ANY_VALUE, ANY_VALUE);

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // test case: When invoking the getCloudUser method with a credential map
    // without a password, it must throw an UnauthenticatedUserException.
    @Test(expected = UnauthenticatedUserException.class) // verify
    public void testGetCloudUserWithoutPassword() throws FogbowException {
        // set up
        Map<String, String> credentials = createCredentials(ANY_VALUE, null, ANY_VALUE, ANY_VALUE);

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // test case: When invoking the getCloudUser method with a credential map
    // without a domain, it must throw an UnauthenticatedUserException.
    @Test(expected = UnauthenticatedUserException.class) // verify
    public void testGetCloudUserWithoutDomain() throws FogbowException {
        // set up
        Map<String, String> credentials = createCredentials(ANY_VALUE, ANY_VALUE, null, ANY_VALUE);

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // test case: When invoking the getCloudUser method with a credential map
    // without a domain, it must throw an UnauthenticatedUserException.
    @Test(expected = UnauthenticatedUserException.class) // verify
    public void testGetCloudUserWithoutProject() throws FogbowException {
        // set up
        Map<String, String> credentials = createCredentials(ANY_VALUE, ANY_VALUE, ANY_VALUE, ANY_VALUE);

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // test case: When invoking the getCloudUser method with a credential map
    // with correct data, without providing projectId, it should return a unscoped token
    @Test
    public void testGetCloudUserUnscoped() throws FogbowException {
        // setup
        OpenStackV3User fakeToken = new OpenStackV3User(ANY_VALUE, ANY_VALUE, ANY_VALUE, null);
        mockServices(fakeToken);
        Map<String, String> credentials = createCredentials(ANY_VALUE, ANY_VALUE, ANY_VALUE, null);

        // exercise
        this.plugin.getCloudUser(credentials);

        // verify
        Mockito.verify(this.plugin).getUnscopedCloudUserFromJson(Mockito.any());
    }

    // test case: When invoking the getCloudUser method with a credential map
    // with correct data, providing projectId, it should return a scoped token
    @Test
    public void testGetCloudUserScoped() throws FogbowException {
        // setup
        OpenStackV3User fakeToken = new OpenStackV3User(ANY_VALUE, ANY_VALUE, ANY_VALUE, ANY_VALUE);
        mockServices(fakeToken);
        Map<String, String> credentials = createCredentials(ANY_VALUE, ANY_VALUE, ANY_VALUE, ANY_VALUE);

        // exercise
        this.plugin.getCloudUser(credentials);

        // verify
        Mockito.verify(this.plugin).getCloudUserFromJson(Mockito.any());
    }

    private void mockServices(OpenStackV3User openStackV3User) throws FogbowException {
        HttpResponse response = new HttpResponse(ANY_VALUE, HttpStatus.OK.value(), new HashMap<>());
        PowerMockito.mockStatic(HttpRequestClient.class);
        BDDMockito.when(HttpRequestClient.doGenericRequest(Mockito.anyObject(), Mockito.anyString(), Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(response);

        Mockito.doReturn(openStackV3User).when(this.plugin)
                .getCloudUserFromJson(response);

        Mockito.doReturn(openStackV3User).when(this.plugin)
                .getUnscopedCloudUserFromJson(response);
    }

    private Map<String, String> createCredentials(String username, String password, String domain, String project) {
        Map<String, String> credentials = new HashMap<String, String>();

        credentials.put(OpenStackConstants.Identity.USER_NAME_KEY_JSON, username);
        credentials.put(OpenStackConstants.Identity.PASSWORD_KEY_JSON, password);
        credentials.put(OpenStackConstants.Identity.DOMAIN_KEY_JSON, domain);
        credentials.put(OpenStackConstants.Identity.PROJECT_NAME_KEY_JSON, project);

        return credentials;
    }
}