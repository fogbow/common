package cloud.fogbow.common.plugins.cloudidp.aws.v2;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cloud.fogbow.common.constants.AwsConstants;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.AwsV2User;

public class AwsIdentityProviderPluginTest {

	private static final String ANY_VALUE = "anything";
	private static final String FAKE_USER_ID = "fake-user-id";
	
	private AwsIdentityProviderPlugin plugin;
	
	@Before
	public void setUp() {
		this.plugin = Mockito.spy(new AwsIdentityProviderPlugin());
	}
	
	// test case: When invoking the getCloudUser method with an empty user
	// credential map, it must throw an InvalidParameterException.
	@Test(expected = InvalidParameterException.class) // verify
	public void testGetCloudUserWithEmptyUserCredentials() throws FogbowException {
		// set up
		Map<String, String> emptyUserCredentials = new HashMap<String, String>();

		// exercise
		this.plugin.getCloudUser(emptyUserCredentials);
	}
    
	// test case: When invoking the getCloudUser method with a map of the user's
	// credentials, without an access key, it must throw an
	// InvalidParameterException.
	@Test(expected = InvalidParameterException.class) // verify
	public void testGetCloudUserWithoutAccessKey() throws FogbowException {
		// set up
		Map<String, String> userCredentials = new HashMap<String, String>();
		userCredentials.put(AwsConstants.SECRET_ACCESS_KEY, ANY_VALUE);

		// exercise
		this.plugin.getCloudUser(userCredentials);
	}
	
	// test case: When invoking the getCloudUser method with a map of the user's
	// credentials, without a secret access key, it must throw an
	// InvalidParameterException.
	@Test(expected = InvalidParameterException.class) // verify
	public void testGetCloudUserWithoutSecretAccessKey() throws FogbowException {
		// set up
		Map<String, String> userCredentials = new HashMap<String, String>();
		userCredentials.put(AwsConstants.ACCESS_KEY, ANY_VALUE);

		// exercise
		this.plugin.getCloudUser(userCredentials);
	}
    
	// test case: When invoking the getCloudUser method with a map of invalid user
	// credentials, it must throw an UnauthenticatedUserException.
	@Test(expected = UnauthenticatedUserException.class) // verify
	public void testGetCloudUserWithInvalidUserCredentials() throws FogbowException {
		// set up
		Map<String, String> userCredentials = new HashMap<String, String>();
		userCredentials.put(AwsConstants.ACCESS_KEY, ANY_VALUE);
		userCredentials.put(AwsConstants.SECRET_ACCESS_KEY, ANY_VALUE);
		
		// exercise
		this.plugin.getCloudUser(userCredentials);
	}
	
	// test case: When invoking the getCloudUser method with a map of valid user
	// credentials, this must return the cloud user.
	@Test
	public void testGetCloudUserSuccessful() throws FogbowException {
		// set up
		Map<String, String> userCredentials = new HashMap<String, String>();
		userCredentials.put(AwsConstants.ACCESS_KEY, ANY_VALUE);
		userCredentials.put(AwsConstants.SECRET_ACCESS_KEY, ANY_VALUE);

		Mockito.doReturn(FAKE_USER_ID).when(this.plugin).authenticate(Mockito.eq(ANY_VALUE), Mockito.eq(ANY_VALUE));
		AwsV2User expected = createCloudUser();

		// exercise
		AwsV2User cloudUser = this.plugin.getCloudUser(userCredentials);

		// verify
		Mockito.verify(this.plugin, Mockito.times(1)).authenticate(Mockito.eq(ANY_VALUE), Mockito.eq(ANY_VALUE));

		Assert.assertEquals(expected.getId(), cloudUser.getId());
		Assert.assertEquals(expected.getName(), cloudUser.getName());
		Assert.assertEquals(expected.getToken(), cloudUser.getToken());
	}
	
	private AwsV2User createCloudUser() {
		return new AwsV2User(FAKE_USER_ID, FAKE_USER_ID, ANY_VALUE, ANY_VALUE);
	}

}
