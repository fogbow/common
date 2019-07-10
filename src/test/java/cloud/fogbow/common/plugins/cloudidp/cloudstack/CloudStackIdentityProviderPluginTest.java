package cloud.fogbow.common.plugins.cloudidp.cloudstack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cloud.fogbow.common.constants.CloudStackConstants;
import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.util.connectivity.HttpRequestClient;
import cloud.fogbow.common.util.connectivity.HttpResponse;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ HttpRequestClient.class })
public class CloudStackIdentityProviderPluginTest {

	private static final String ANY_VALUE = "anything";
	private static final String FAKE_URL = "http://localhost:8080/cloudstack";
	
	private static final int HTTP_CODE_ERROR = 400;
	private static final int HTTP_CODE_OK = 200;

	private CloudStackIdentityProviderPlugin plugin;

	@Before
	public void setUp() {
		String cloudStackUrl = FAKE_URL;
		this.plugin = new CloudStackIdentityProviderPlugin(cloudStackUrl);
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
		HttpResponse loginResponse = createLoginResponse(httpCode);

		PowerMockito.mockStatic(HttpRequestClient.class);
		BDDMockito.given(HttpRequestClient.doGenericRequest(Mockito.eq(HttpMethod.POST), Mockito.anyString(),
				Mockito.any(), Mockito.any())).willReturn(loginResponse);

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

		PowerMockito.mockStatic(HttpRequestClient.class);
		BDDMockito.given(HttpRequestClient.doGenericRequest(Mockito.eq(HttpMethod.POST), Mockito.anyString(),
				Mockito.any(), Mockito.any())).willReturn(loginResponse);

		int httpCodeError = HTTP_CODE_ERROR;
		HttpResponse listAccountsResponse = createListAccountsResponse(httpCodeError);

		BDDMockito.given(HttpRequestClient.doGenericRequest(Mockito.eq(HttpMethod.GET), Mockito.anyString(),
				Mockito.any(), Mockito.any())).willReturn(listAccountsResponse);

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

		PowerMockito.mockStatic(HttpRequestClient.class);
		BDDMockito.given(HttpRequestClient.doGenericRequest(Mockito.eq(HttpMethod.POST), Mockito.anyString(),
				Mockito.any(), Mockito.any())).willReturn(loginResponse);

		HashMap<String, List<String>> headers = createHeadersMap();
		String content = ANY_VALUE;
		HttpResponse listAccountsResponse = new HttpResponse(content, httpCodeOk, headers);

		BDDMockito.given(HttpRequestClient.doGenericRequest(Mockito.eq(HttpMethod.GET), Mockito.anyString(),
				Mockito.any(), Mockito.any())).willReturn(listAccountsResponse);

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

		PowerMockito.mockStatic(HttpRequestClient.class);
		BDDMockito.given(HttpRequestClient.doGenericRequest(Mockito.eq(HttpMethod.POST), Mockito.anyString(),
				Mockito.any(), Mockito.any())).willReturn(loginResponse);

		HttpResponse listAccountsResponse = createListAccountsResponse(httpCodeOk);

		BDDMockito.given(HttpRequestClient.doGenericRequest(Mockito.eq(HttpMethod.GET), Mockito.anyString(),
				Mockito.any(), Mockito.any())).willReturn(listAccountsResponse);

		// exercise
		this.plugin.getCloudUser(credentials);

		// verify
		PowerMockito.verifyStatic(HttpRequestClient.class, Mockito.times(2));
		HttpRequestClient.doGenericRequest(Mockito.any(), Mockito.anyString(), Mockito.any(), Mockito.any());
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
