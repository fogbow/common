package cloud.fogbow.common.plugins.cloudidp.openstack.v3;

import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.constants.OpenStackConstants;
import cloud.fogbow.common.exceptions.UnexpectedException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class OpenStackIdentityProviderPluginTest {

    private static final String ANY_VALUE = "anything";
    private static final String FAKE_URL = "http://localhost:000";
    private OpenStackIdentityProviderPlugin plugin;

    @Before
    public void setup() {
        this.plugin = new OpenStackIdentityProviderPlugin(FAKE_URL);
    }

    // test case: When invoking the getCloudUser method with a null credential map,
    // it must throw an UnexpectedException.
    @Test(expected = NullPointerException.class) // verify
    public void testgetCloudUserWithNullCredentialsMap() throws FogbowException {
        // set up
        Map<String, String> credentials = null;

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // test case: When invoking the getCloudUser method with an empty credential
    // map, it must throw an UnexpectedException.
    @Test(expected = UnexpectedException.class) // verify
    public void testgetCloudUserWithEmptyCredentialsMap() throws FogbowException {
        // set up
        Map<String, String> credentials = new HashMap<String, String>();

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // test case: When invoking the getCloudUser method with a credential map
    // without a user name, it must throw an UnexpectedException.
    @Test(expected = UnexpectedException.class) // verify
    public void testgetCloudUserWithoutUsername() throws FogbowException {
        // set up
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put(OpenStackConstants.Identity.PASSWORD_KEY_JSON, ANY_VALUE);
        credentials.put(OpenStackConstants.Identity.DOMAIN_KEY_JSON, ANY_VALUE);
        credentials.put(OpenStackConstants.Identity.PROJECT_NAME_KEY_JSON, ANY_VALUE);

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // test case: When invoking the getCloudUser method with a credential map
    // without a password, it must throw an UnexpectedException.
    @Test(expected = UnexpectedException.class) // verify
    public void testgetCloudUserWithoutPassword() throws FogbowException {
        // set up
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put(OpenStackConstants.Identity.USER_NAME_KEY_JSON, ANY_VALUE);
        credentials.put(OpenStackConstants.Identity.DOMAIN_KEY_JSON, ANY_VALUE);
        credentials.put(OpenStackConstants.Identity.PROJECT_NAME_KEY_JSON, ANY_VALUE);

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // test case: When invoking the getCloudUser method with a credential map
    // without a domain, it must throw an UnexpectedException.
    @Test(expected = UnexpectedException.class) // verify
    public void testgetCloudUserWithoutDomain() throws FogbowException {
        // set up
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put(OpenStackConstants.Identity.USER_NAME_KEY_JSON, ANY_VALUE);
        credentials.put(OpenStackConstants.Identity.PASSWORD_KEY_JSON, ANY_VALUE);
        credentials.put(OpenStackConstants.Identity.PROJECT_NAME_KEY_JSON, ANY_VALUE);

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    // test case: When invoking the getCloudUser method with a credential map
    // without a domain, it must throw an UnexpectedException.
    @Test(expected = UnexpectedException.class) // verify
    public void testgetCloudUserWithoutProject() throws FogbowException {
        // set up
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put(OpenStackConstants.Identity.USER_NAME_KEY_JSON, ANY_VALUE);
        credentials.put(OpenStackConstants.Identity.PASSWORD_KEY_JSON, ANY_VALUE);
        credentials.put(OpenStackConstants.Identity.DOMAIN_KEY_JSON, ANY_VALUE);

        // exercise
        this.plugin.getCloudUser(credentials);
    }

    
}