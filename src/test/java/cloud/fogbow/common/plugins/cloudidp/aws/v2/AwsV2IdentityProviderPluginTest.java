package cloud.fogbow.common.plugins.cloudidp.aws.v2;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cloud.fogbow.common.constants.AwsConstants;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.AwsV2User;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.IamClientBuilder;
import software.amazon.awssdk.services.iam.model.GetUserResponse;
import software.amazon.awssdk.services.iam.model.User;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AwsBasicCredentials.class, IamClient.class, StaticCredentialsProvider.class })
public class AwsV2IdentityProviderPluginTest {

	private static final String ANY_VALUE = "anything";
	private static final String FAKE_USER_ID = "fake-user-id";
	
	private AwsV2IdentityProviderPlugin plugin;
	
	@Before
	public void setUp() {
		this.plugin = new AwsV2IdentityProviderPlugin();
	}
	
	// test case: When invoking the getCloudUser method with an empty user
	// credential map, it must throw an InvalidParameterException.
	@Test(expected = InvalidParameterException.class) // verify
	public void testGetCloudUserWithEmptyUserCredentials() throws FogbowException {
		// set up
		Map<String, String> userCredentials = new HashMap<String, String>();

		// exercise
		this.plugin.getCloudUser(userCredentials);
	}
    
	// test case: When invoking the getCloudUser method with a map of the user's
	// credentials, without an access key, it must throw an
	// InvalidParameterException.
	@Test(expected = InvalidParameterException.class) // verify
	public void testtestGetCloudUserWithoutAccessKey() throws FogbowException {
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
	public void testGetCloudUserWithInvalidUserCredenctials() throws FogbowException {
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

		AwsBasicCredentials credentials = Mockito.mock(AwsBasicCredentials.class);
		PowerMockito.mockStatic(AwsBasicCredentials.class);
		BDDMockito.given(AwsBasicCredentials.create(Mockito.eq(ANY_VALUE), Mockito.eq(ANY_VALUE)))
				.willReturn(credentials);

		IamClient client = buildIamClientMocked(credentials);

		GetUserResponse response = buildGetUserResponse();
		Mockito.when(client.getUser()).thenReturn(response);

		String expected = FAKE_USER_ID;

		// exercise
		AwsV2User cloudUser = this.plugin.getCloudUser(userCredentials);

		// verify
		PowerMockito.verifyStatic(AwsBasicCredentials.class, Mockito.times(1));
		AwsBasicCredentials.create(Mockito.eq(ANY_VALUE), Mockito.eq(ANY_VALUE));

		PowerMockito.verifyStatic(StaticCredentialsProvider.class, Mockito.times(1));
		StaticCredentialsProvider.create(Mockito.eq(credentials));

		Mockito.verify(client, Mockito.times(1)).getUser();

		Assert.assertEquals(expected, cloudUser.getId());
	}

	private IamClient buildIamClientMocked(AwsBasicCredentials credentials) {
		StaticCredentialsProvider provider = Mockito.mock(StaticCredentialsProvider.class);
		PowerMockito.mockStatic(StaticCredentialsProvider.class);
		BDDMockito.given(StaticCredentialsProvider.create(Mockito.eq(credentials))).willReturn(provider);

		IamClient client = Mockito.mock(IamClient.class);
		IamClientBuilder clientBuilder = Mockito.mock(IamClientBuilder.class);
		PowerMockito.mockStatic(IamClient.class);
		Mockito.when(clientBuilder.credentialsProvider(Mockito.eq(provider))).thenReturn(clientBuilder);
		Mockito.when(clientBuilder.region(Mockito.eq(Region.AWS_GLOBAL))).thenReturn(clientBuilder);
		BDDMockito.given(IamClient.builder()).willReturn(clientBuilder);
		BDDMockito.given(clientBuilder.build()).willReturn(client);
		return client;
	}

	private GetUserResponse buildGetUserResponse() {
		String userId = FAKE_USER_ID;
		User user = User.builder().userId(userId).build();
		return GetUserResponse.builder().user(user).build();
	}
    
}
