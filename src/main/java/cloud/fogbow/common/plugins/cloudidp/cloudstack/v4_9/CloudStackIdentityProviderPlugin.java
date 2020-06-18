package cloud.fogbow.common.plugins.cloudidp.cloudstack.v4_9;

import cloud.fogbow.common.constants.CloudStackConstants;
import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.CloudStackUser;
import cloud.fogbow.common.plugins.cloudidp.CloudIdentityProviderPlugin;
import cloud.fogbow.common.util.connectivity.HttpErrorConditionToFogbowExceptionMapper;
import cloud.fogbow.common.util.connectivity.HttpRequestClient;
import cloud.fogbow.common.util.connectivity.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudStackIdentityProviderPlugin implements CloudIdentityProviderPlugin<CloudStackUser> {
    private static final Logger LOGGER = Logger.getLogger(CloudStackIdentityProviderPlugin.class);
    private static final String COOKIE_HEADER = "Cookie";
    private static final String STRING_SPACE = " ";
    protected static final String COOKIE_SEPARATOR = ";";
    protected static final String SET_COOKIE_HEADER_1 = "Set-Cookie";
    protected static final String SET_COOKIE_HEADER_2 = "SET-COOKIE";

    private String cloudStackUrl;

    public CloudStackIdentityProviderPlugin(String cloudStackUrl) {
        this.cloudStackUrl = cloudStackUrl;
    }

    @Override
    public CloudStackUser getCloudUser(Map<String, String> credentials) throws UnauthenticatedUserException {
        checkCredentials(credentials);

        try {
            LoginRequest request = createLoginRequest(credentials);
            HttpResponse response = authenticate(request);
            Map<String, String> cookieHeaders = getCookieHeaders(response);

            return getCloudStackUser(LoginResponse.fromJson(response.getContent()), cookieHeaders);
        } catch (Exception e) {
            throw new UnauthenticatedUserException(e.getMessage());
        }
    }

    protected HttpResponse authenticate(LoginRequest request) throws UnauthenticatedUserException {
        // Since all cloudstack requests params are passed via url args, we do not need
        // to send a valid json body in the post request
        try {
            return HttpRequestClient.doGenericRequest(HttpMethod.POST, request.getUriBuilder().toString(),
                    new HashMap<>(), new HashMap<>());
        } catch (FogbowException e) {
            throw new UnauthenticatedUserException(e.getMessage());
        }
    }

    private void checkCredentials(Map<String, String> credentials) throws UnauthenticatedUserException {
        if (credentials == null
                || credentials.get(CloudStackConstants.Identity.USERNAME_KEY_JSON) == null
                || credentials.get(CloudStackConstants.Identity.PASSWORD_KEY_JSON) == null
                || credentials.get(CloudStackConstants.Identity.DOMAIN_KEY_JSON) == null) {
            throw new UnauthenticatedUserException(Messages.Exception.NO_USER_CREDENTIALS);
        }
    }

    private LoginRequest createLoginRequest(Map<String, String> credentials) throws UnauthenticatedUserException {
        String userId = credentials.get(CloudStackConstants.Identity.USERNAME_KEY_JSON);
        String password = credentials.get(CloudStackConstants.Identity.PASSWORD_KEY_JSON);
        String domain = credentials.get(CloudStackConstants.Identity.DOMAIN_KEY_JSON);

        LoginRequest loginRequest = new LoginRequest.Builder()
                .username(userId)
                .password(password)
                .domain(domain)
                .build(this.cloudStackUrl);

        return loginRequest;
    }

    private HashMap getCookieHeaders(HttpResponse response) {
        HashMap<String, String> cookieHeaders = new HashMap<>();
        Map<String, List<String>> headerFields = response.getHeaders();
        if (headerFields != null) {
            String setCookieHeaders1 = null;
            String setCookieHeaders2 = null;
            List<String> cookieHeader1 = headerFields.get(SET_COOKIE_HEADER_1);
            List<String> cookieHeader2 = headerFields.get(SET_COOKIE_HEADER_2);
            if (cookieHeader1 != null) {
                setCookieHeaders1 = String.join(COOKIE_SEPARATOR, cookieHeader1);
            }
            if (cookieHeader2 != null) {
                setCookieHeaders2 = String.join(COOKIE_SEPARATOR, cookieHeader2);
            }
            if (setCookieHeaders1 != null) {
                if (setCookieHeaders2 != null) {
                    String cookieHeadersValue = String.join(COOKIE_SEPARATOR, setCookieHeaders1, setCookieHeaders2);
                    cookieHeaders.put(COOKIE_HEADER, cookieHeadersValue);
                } else {
                    String cookieHeadersValue = String.join(COOKIE_SEPARATOR, setCookieHeaders1);
                    cookieHeaders.put(COOKIE_HEADER, cookieHeadersValue);
                }
            } else {
                if (setCookieHeaders2 != null) {
                    String cookieHeadersValue = String.join(COOKIE_SEPARATOR, setCookieHeaders2);
                    cookieHeaders.put(COOKIE_HEADER, cookieHeadersValue);
                }
            }
        }
        return cookieHeaders;
    }

    private CloudStackUser getCloudStackUser(LoginResponse loginAuthentication, Map<String, String> cookieHeaders)
            throws UnauthenticatedUserException {
        HttpResponse response = null;

        try {
            String sessionKey = loginAuthentication.getSessionKey();
            ListAccountsRequest request = new ListAccountsRequest.Builder().sessionKey(sessionKey).build(this.cloudStackUrl);

            response = doGenerateAccountsList(request, cookieHeaders);
        } catch (FogbowException e) {
            throw new UnauthenticatedUserException(e.getMessage());
        }

        if (response.getHttpCode() > HttpStatus.SC_OK) {
            throw new UnauthenticatedUserException(response.getContent());
        } else {
            ListAccountsResponse listAccountsResponse = ListAccountsResponse.fromJson(response.getContent());
                return mountCloudStackUser(listAccountsResponse, cookieHeaders);
        }
    }

    private CloudStackUser mountCloudStackUser(ListAccountsResponse response, Map<String, String> cookieHeaders) {
        // Considering only one account/user per request
        ListAccountsResponse.User user = response.getAccounts().get(0).getUsers().get(0);

        // Keeping the token-value separator as expected by the other cloudstack plugins
        String tokenValue = user.getApiKey() + CloudStackConstants.KEY_VALUE_SEPARATOR + user.getSecretKey();
        String userId = user.getId();
        String userName = getUserName(user);
        String domain = user.getDomain();
        CloudStackUser cloudStackUser = new CloudStackUser(userId, userName, tokenValue, domain, cookieHeaders);
        return cloudStackUser;
    }

    protected HttpResponse doGenerateAccountsList(ListAccountsRequest request, Map<String, String> cookieHeaders)
            throws FogbowException {

        HttpResponse response = HttpRequestClient.doGenericRequest(HttpMethod.GET, request.getUriBuilder().toString(),
                cookieHeaders, new HashMap<>());

        return response;
    }

    private String getUserName(ListAccountsResponse.User user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        return (firstName != null && lastName != null) ? firstName + STRING_SPACE + lastName : user.getUsername();
    }
}
