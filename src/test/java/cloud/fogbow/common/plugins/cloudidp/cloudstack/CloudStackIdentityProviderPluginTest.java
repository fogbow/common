package cloud.fogbow.common.plugins.cloudidp.cloudstack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cloud.fogbow.common.constants.CloudStackConstants;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.CloudStackUser;
import cloud.fogbow.common.util.connectivity.HttpResponse;

public class CloudStackIdentityProviderPluginTest {

	private static final String ANY_VALUE = "anything";
	private static final String COOKIE_KEY = "Cookie";
	private static final String FAKE_DOMAIN = "adomain";
	private static final String FAKE_ID = "anid";
	private static final String FAKE_NAME = "Jon Doe";
	private static final String FAKE_TOKEN = "anapikey:asecretkey";
	private static final String FAKE_URL = "http://localhost:8080/cloudstack";
	
	private static final int HTTP_CODE_ERROR = 400;
	private static final int HTTP_CODE_OK = 200;

	private CloudStackIdentityProviderPlugin plugin;

	@Before
	public void setUp() {
		String cloudStackUrl = FAKE_URL;
		this.plugin = Mockito.spy(new CloudStackIdentityProviderPlugin(cloudStackUrl));
	}

	// test case: When invoking the getCloudUser method with a null credential map,
	// it must throw an InvalidParameterException.
	@Test(expected = InvalidParameterException.class) // verify
	public void testGetCloudUserWithNullCredentialsMap() throws FogbowException {
		// set up
		Map<String, String> credentials = null;

		// exercise
		this.plugin.getCloudUser(credentials);
	}
		
	// test case: When invoking the getCloudUser method with an empty credential
	// map, it must throw an InvalidParameterException.
	@Test(expected = InvalidParameterException.class) // verify
	public void testGetCloudUserWithEmptyCredentialsMap() throws FogbowException {
		// set up
		Map<String, String> credentials = new HashMap<String, String>();

		// exercise
		this.plugin.getCloudUser(credentials);
	}

	// test case: When invoking the getCloudUser method with a credential map
	// without a user name, it must throw an InvalidParameterException.
	@Test(expected = InvalidParameterException.class) // verify
	public void testGetCloudUserWithoutUsername() throws FogbowException {
		// set up
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put(CloudStackConstants.Identity.PASSWORD_KEY_JSON, ANY_VALUE);
		credentials.put(CloudStackConstants.Identity.DOMAIN_KEY_JSON, ANY_VALUE);

		// exercise
		this.plugin.getCloudUser(credentials);
	}

	// test case: When invoking the getCloudUser method with a credential map
	// without a password, it must throw an InvalidParameterException.
	@Test(expected = InvalidParameterException.class) // verify
	public void testGetCloudUserWithoutPassword() throws FogbowException {
		// set up
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put(CloudStackConstants.Identity.USERNAME_KEY_JSON, ANY_VALUE);
		credentials.put(CloudStackConstants.Identity.DOMAIN_KEY_JSON, ANY_VALUE);

		// exercise
		this.plugin.getCloudUser(credentials);
	}

	// test case: When invoking the getCloudUser method with a credential map
	// without a domain, it must throw an InvalidParameterException.
	@Test(expected = InvalidParameterException.class) // verify
	public void testGetCloudUserWithoutDomain() throws FogbowException {
		// set up
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put(CloudStackConstants.Identity.USERNAME_KEY_JSON, ANY_VALUE);
		credentials.put(CloudStackConstants.Identity.PASSWORD_KEY_JSON, ANY_VALUE);

		// exercise
		this.plugin.getCloudUser(credentials);
	}

	// test case: When invoking the getCloudUser method with a valid credential map,
	// and the response to the login request in the server returns an HTTP error
	// code, it must throw a FogbowException.
	@Test(expected = FogbowException.class)
	public void testGetCloudUserWithHttpCodeErrorFromLoginResponse() throws FogbowException {
		// set up
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put(CloudStackConstants.Identity.USERNAME_KEY_JSON, ANY_VALUE);
		credentials.put(CloudStackConstants.Identity.PASSWORD_KEY_JSON, ANY_VALUE);
		credentials.put(CloudStackConstants.Identity.DOMAIN_KEY_JSON, ANY_VALUE);

		int httpCode = HTTP_CODE_ERROR;
		HttpResponse response = createLoginResponse(httpCode);
		Mockito.doReturn(response).when(this.plugin).doLoginAuthentication(Mockito.any(LoginRequest.class));

		// exercise
		this.plugin.getCloudUser(credentials);
	}
	
	// test case: When invoking the getCloudUser method with a valid credential map,
	// and the response returns an HTTP error code from an accounts list request in
	// the server, it must throw a FogbowException.
	@Test(expected = FogbowException.class) // verify
	public void testGetCloudUserWithHttpCodeErrorFromListAccountsResponse() throws FogbowException {
		// set up
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put(CloudStackConstants.Identity.USERNAME_KEY_JSON, ANY_VALUE);
		credentials.put(CloudStackConstants.Identity.PASSWORD_KEY_JSON, ANY_VALUE);
		credentials.put(CloudStackConstants.Identity.DOMAIN_KEY_JSON, ANY_VALUE);

		int httpCodeOk = HTTP_CODE_OK;
		HttpResponse loginResponse = createLoginResponse(httpCodeOk);
		Mockito.doReturn(loginResponse).when(this.plugin).doLoginAuthentication(Mockito.any(LoginRequest.class));

		int httpCodeError = HTTP_CODE_ERROR;
		HttpResponse listAccountsResponse = createListAccountsResponse(httpCodeError);
		Mockito.doReturn(listAccountsResponse).when(this.plugin).doGenerateAccountsList(Mockito.any(ListAccountsRequest.class));

		// exercise
		this.plugin.getCloudUser(credentials);
	}
    
	// test case: When invoking the getCloudUser method with a valid credential map,
	// and the response returns an invalid content from an accounts list request in
	// the server, it must throw an UnexpectedException.
	@Test(expected = UnexpectedException.class) // verify
	public void testGetCloudUserWithInvalidContentFromListAccountsResponse() throws FogbowException {
		// set up
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put(CloudStackConstants.Identity.USERNAME_KEY_JSON, ANY_VALUE);
		credentials.put(CloudStackConstants.Identity.PASSWORD_KEY_JSON, ANY_VALUE);
		credentials.put(CloudStackConstants.Identity.DOMAIN_KEY_JSON, ANY_VALUE);

		int httpCodeOk = HTTP_CODE_OK;
		HttpResponse loginResponse = createLoginResponse(httpCodeOk);
		Mockito.doReturn(loginResponse).when(this.plugin).doLoginAuthentication(Mockito.any(LoginRequest.class));

		HashMap<String, List<String>> headers = createHeadersMap();
		String content = ANY_VALUE;
		HttpResponse listAccountsResponse = new HttpResponse(content, httpCodeOk, headers);
		Mockito.doReturn(listAccountsResponse).when(this.plugin).doGenerateAccountsList(Mockito.any(ListAccountsRequest.class));

		// exercise
		this.plugin.getCloudUser(credentials);
	}
	
	// test case: When invoking the getCloudUser method with a valid credential map,
	// and the responses the requests to the server return an HTTP Ok status with
	// valid content, it must return a cloud stack user.
	@Test
	public void testGetCloudUserSuccessful() throws FogbowException {
		// set up
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put(CloudStackConstants.Identity.USERNAME_KEY_JSON, ANY_VALUE);
		credentials.put(CloudStackConstants.Identity.PASSWORD_KEY_JSON, ANY_VALUE);
		credentials.put(CloudStackConstants.Identity.DOMAIN_KEY_JSON, ANY_VALUE);

		int httpCodeOk = HTTP_CODE_OK;
		HttpResponse loginResponse = createLoginResponse(httpCodeOk);
		Mockito.doReturn(loginResponse).when(this.plugin).doLoginAuthentication(Mockito.any(LoginRequest.class));

		HttpResponse listAccountsResponse = createListAccountsResponse(httpCodeOk);
		Mockito.doReturn(listAccountsResponse).when(this.plugin)
				.doGenerateAccountsList(Mockito.any(ListAccountsRequest.class));

		CloudStackUser expectedUser = createCloudStackUser();

		// exercise
		CloudStackUser cloudUser = this.plugin.getCloudUser(credentials);

		// verify
		Assert.assertEquals(expectedUser.getId(), cloudUser.getId());
		Assert.assertEquals(expectedUser.getName(), cloudUser.getName());
		Assert.assertEquals(expectedUser.getToken(), cloudUser.getToken());
		Assert.assertEquals(expectedUser.getDomain(), cloudUser.getDomain());
		Assert.assertEquals(expectedUser.getCookieHeaders().get(COOKIE_KEY).toString(),
				cloudUser.getCookieHeaders().get(COOKIE_KEY).toString());
	}

	private CloudStackUser createCloudStackUser() {
		String userId = FAKE_ID;
		String userName = FAKE_NAME;
		String tokenValue = FAKE_TOKEN;
		String domain = FAKE_DOMAIN;
		String value = CloudStackIdentityProviderPlugin.COOKIE_SEPARATOR + ANY_VALUE;
		String cookies = ANY_VALUE;
		for (int i = 0; i < 5; i++) {
			cookies += value;
		}
		Map<String, String> cookieHeaders = new HashMap<>();
		cookieHeaders.put(COOKIE_KEY, cookies);
		return new CloudStackUser(userId, userName, tokenValue, domain, cookieHeaders);
	}

	private HttpResponse createListAccountsResponse(int httpCode) {
		HashMap<String, List<String>> headers = createHeadersMap();
		String content = generateListAccountsContent();
		return new HttpResponse(content, httpCode, headers);
	}

	private String generateListAccountsContent() {
		return "{\n" + 
				" \"listaccountsresponse\": {\n" + 
				" \"count\": 1,\n" + 
				" \"account\": [{\n" + 
				" \"user\": [{\n" + 
				" \"id\": \"anid\",\n" + 
				" \"username\": \"ausername@usernames.com\",\n" + 
				" \"firstname\": \"Jon\",\n" + 
				" \"lastname\": \"Doe\",\n" + 
				" \"email\": \"anemail@emails.com\",\n" + 
				" \"created\": \"2016-10-17T12:28:48-0200\",\n" + 
				" \"state\": \"enabled\",\n" + 
				" \"account\": \"account@accounts.com\",\n" + 
				" \"accounttype\": 2,\n" + 
				" \"roleid\": \"785e2b66-36b0-11e7-a516-0e043877b6cb\",\n" + 
				" \"roletype\": \"DomainAdmin\",\n" + 
				" \"rolename\": \"Domain Admin\",\n" + 
				" \"domainid\": \"adomainid\",\n" + 
				" \"domain\": \"adomain\",\n" + 
				" \"apikey\": \"anapikey\",\n" + 
				" \"secretkey\": \"asecretkey\",\n" + 
				" \"accountid\": \"anaccountid\",\n" + 
				" \"iscallerchilddomain\": false,\n" + 
				" \"isdefault\": false\n" + 
				" }],\n" + 
				" \"isdefault\": false,\n" + 
				" \"groups\": []}]\n" + 
				" }\n" + 
				" }";
	}

	private HttpResponse createLoginResponse(int httpCode) {
		HashMap<String, List<String>> headers = createHeadersMap();
		String content = generateLoginResponseContent();
		return new HttpResponse(content, httpCode, headers);
	}

	private String generateLoginResponseContent() {
		return "{\n" + 
				" \"loginresponse\": {\n" + 
				" \"username\": \"user@myaddr.com\",\n" + 
				" \"userid\": \"userid\",\n" + 
				" \"domainid\": \"domainid\",\n" + 
				" \"timeout\": 1800,\n" + 
				" \"account\": \"user@myaddr.com\",\n" + 
				" \"firstname\": \"Jon\",\n" + 
				" \"lastname\": \"Doe\",\n" + 
				" \"type\": \"2\",\n" + 
				" \"registered\": \"false\",\n" + 
				" \"sessionkey\": \"DC_08S8ALd85JixeRU4as5jHxLE\"\n" + 
				" }\n" + 
				" }";
	}
	
	private HashMap<String, List<String>> createHeadersMap() {
		List<String> cookies = new ArrayList<>();
		cookies.add(ANY_VALUE);
		cookies.add(ANY_VALUE);
		cookies.add(ANY_VALUE);

		HashMap<String, List<String>> headers = new HashMap<String, List<String>>();
		headers.put(CloudStackIdentityProviderPlugin.SET_COOKIE_HEADER_1, cookies);
		headers.put(CloudStackIdentityProviderPlugin.SET_COOKIE_HEADER_2, cookies);
		return headers;
	}
	
}
