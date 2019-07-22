package cloud.fogbow.common.plugins.cloudidp.openstack.v3;

import cloud.fogbow.common.constants.HttpConstants;
import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.constants.OpenStackConstants;
import cloud.fogbow.common.exceptions.FatalErrorException;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.OpenStackV3User;
import cloud.fogbow.common.plugins.cloudidp.CloudIdentityProviderPlugin;
import cloud.fogbow.common.util.GsonHolder;
import cloud.fogbow.common.util.connectivity.HttpRequestClient;
import cloud.fogbow.common.util.connectivity.HttpResponse;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenStackIdentityProviderPlugin implements CloudIdentityProviderPlugin<OpenStackV3User> {
    private static final Logger LOGGER = Logger.getLogger(OpenStackIdentityProviderPlugin.class);

    private String v3TokensEndpoint;

    // Used only in tests
    public OpenStackIdentityProviderPlugin() {
    }

    public OpenStackIdentityProviderPlugin(String identityUrl) throws FatalErrorException {
        if (isUrlValid(identityUrl)) {
            this.v3TokensEndpoint = identityUrl + OpenStackConstants.Identity.V3_TOKENS_ENDPOINT_PATH;
        }
    }

    private boolean isUrlValid(String url) throws FatalErrorException {
        if (url == null || url.trim().isEmpty()) {
            throw new FatalErrorException(String.format(Messages.Fatal.INVALID_SERVICE_URL_S, (url == null ? "null" : "")));
        }
        return true;
    }

    @Override
    public OpenStackV3User getCloudUser(Map<String, String> credentials) throws FogbowException {
        boolean unscopedAuth = credentials.get(OpenStackConstants.Identity.PROJECT_NAME_KEY_JSON) == null;
        String jsonBody = unscopedAuth ? mountUnscopedJsonBody(credentials) : mountJsonBody(credentials);

        HashMap<String, String> body = GsonHolder.getInstance().fromJson(jsonBody, HashMap.class);
        HashMap<String, String> headers = new HashMap<>();
        headers.put(HttpConstants.CONTENT_TYPE_KEY, HttpConstants.JSON_CONTENT_TYPE_KEY);
        headers.put(HttpConstants.ACCEPT_KEY, HttpConstants.JSON_CONTENT_TYPE_KEY);
        HttpResponse response = HttpRequestClient.doGenericRequest(HttpMethod.POST, this.v3TokensEndpoint, headers, body);

        return unscopedAuth ? this.getUnscopedCloudUserFromJson(response) : this.getCloudUserFromJson(response);
    }

    protected OpenStackV3User getCloudUserFromJson(HttpResponse response) throws UnexpectedException {
        String tokenValue = this.getTokenValue(response.getHeaders());

        try {
            CreateAuthenticationResponse createAuthenticationResponse = CreateAuthenticationResponse.fromJson(response.getContent());
            CreateAuthenticationResponse.User userTokenResponse = createAuthenticationResponse.getUser();
            String userId = userTokenResponse.getId();
            String userName = userTokenResponse.getName();
            CreateAuthenticationResponse.Project projectTokenResponse = createAuthenticationResponse.getProject();
            String projectId = projectTokenResponse.getId();
            return new OpenStackV3User(userId, userName, tokenValue, projectId);
        } catch (Exception e) {
            LOGGER.error(Messages.Error.UNABLE_TO_GET_TOKEN_FROM_JSON, e);
            throw new UnexpectedException(Messages.Error.UNABLE_TO_GET_TOKEN_FROM_JSON, e);
        }
    }

    protected OpenStackV3User getUnscopedCloudUserFromJson(HttpResponse response) throws UnexpectedException {
        String tokenValue = this.getTokenValue(response.getHeaders());

        try {
            CreateUnscopedAuthenticationResponse createUnscopedAuthenticationResponse = CreateUnscopedAuthenticationResponse.fromJson(response.getContent());
            CreateUnscopedAuthenticationResponse.User userTokenResponse = createUnscopedAuthenticationResponse.getUser();
            String userId = userTokenResponse.getId();
            String userName = userTokenResponse.getName();
            return new OpenStackV3User(userId, userName, tokenValue, null);
        } catch (Exception e) {
            LOGGER.error(Messages.Error.UNABLE_TO_GET_TOKEN_FROM_JSON, e);
            throw new UnexpectedException(Messages.Error.UNABLE_TO_GET_TOKEN_FROM_JSON, e);
        }
    }

    private String mountJsonBody(Map<String, String> credentials) {
        String projectName = credentials.get(OpenStackConstants.Identity.PROJECT_NAME_KEY_JSON);
        String password = credentials.get(OpenStackConstants.Identity.PASSWORD_KEY_JSON);
        String domain = credentials.get(OpenStackConstants.Identity.DOMAIN_KEY_JSON);
        String userName = credentials.get(OpenStackConstants.Identity.USER_NAME_KEY_JSON);

        CreateAuthenticationRequest createAuthenticationRequest = new CreateAuthenticationRequest.Builder()
                .projectName(projectName)
                .domain(domain)
                .userName(userName)
                .password(password)
                .build();

        return createAuthenticationRequest.toJson();
    }

    private String mountUnscopedJsonBody(Map<String, String> credentials) {
        String password = credentials.get(OpenStackConstants.Identity.PASSWORD_KEY_JSON);
        String domain = credentials.get(OpenStackConstants.Identity.DOMAIN_KEY_JSON);
        String userName = credentials.get(OpenStackConstants.Identity.USER_NAME_KEY_JSON);

        CreateAuthenticationRequest createAuthenticationRequest = new CreateAuthenticationRequest.Builder()
                .domain(domain)
                .userName(userName)
                .password(password)
                .build();

        return createAuthenticationRequest.toJson();
    }

    private String getTokenValue(Map<String, List<String>> headers) {
        String tokenValue = null;
        if (headers.get(OpenStackConstants.X_SUBJECT_TOKEN) != null) {
            List<String> headerValues = headers.get(OpenStackConstants.X_SUBJECT_TOKEN);
            if (!headerValues.isEmpty()) {
                tokenValue = headerValues.get(0);
            } else {
                tokenValue = null;
            }
        }

        return tokenValue;
    }
}
