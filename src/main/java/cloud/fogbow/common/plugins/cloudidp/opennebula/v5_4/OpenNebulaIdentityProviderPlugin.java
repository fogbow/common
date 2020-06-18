package cloud.fogbow.common.plugins.cloudidp.opennebula.v5_4;

import java.util.Map;

import cloud.fogbow.common.exceptions.*;
import org.apache.log4j.Logger;
import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.user.UserPool;

import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.constants.OpenNebulaConstants;
import cloud.fogbow.common.exceptions.InternalServerErrorException;
import cloud.fogbow.common.models.OpenNebulaUser;
import cloud.fogbow.common.plugins.cloudidp.CloudIdentityProviderPlugin;
import cloud.fogbow.common.util.connectivity.cloud.opennebula.OpenNebulaClientUtil;

public class OpenNebulaIdentityProviderPlugin implements CloudIdentityProviderPlugin<OpenNebulaUser> {
    
	private static final Logger LOGGER = Logger.getLogger(OpenNebulaIdentityProviderPlugin.class);

    private String endpoint;
    
    public OpenNebulaIdentityProviderPlugin(String identityUrl) {
    	if (isUrlValid(identityUrl)) {
    		this.endpoint = identityUrl;
    	}
    }
    
    /*
     * The userId is the same as the userName, because the userName is unique in Opennebula
     */
    @Override
    public OpenNebulaUser getCloudUser(Map<String, String> userCredentials) throws UnauthenticatedUserException {
        checkCredentials(userCredentials);

        String username = userCredentials.get(OpenNebulaConstants.USERNAME);
        String openNebulaTokenValue = getOpennebulaTokenValue(userCredentials);

        return new OpenNebulaUser(username, username, openNebulaTokenValue);
    }

    private String getOpennebulaTokenValue(Map<String, String> userCredentials) throws UnauthenticatedUserException {
        String username = userCredentials.get(OpenNebulaConstants.USERNAME);
        String password = userCredentials.get(OpenNebulaConstants.PASSWORD);

        String openNebulaTokenValue = username + OpenNebulaConstants.TOKEN_VALUE_SEPARATOR + password;
        if (!isAuthenticated(openNebulaTokenValue)) {
            LOGGER.error(Messages.Log.AUTHENTICATION_ERROR);
            throw new UnauthenticatedUserException();
        }

        return openNebulaTokenValue;
    }

    private void checkCredentials(Map<String, String> userCredentials) throws UnauthenticatedUserException {
        if (userCredentials == null) {
            throw new UnauthenticatedUserException(Messages.Exception.NO_USER_CREDENTIALS);
        }

        String username = userCredentials.get(OpenNebulaConstants.USERNAME);
        String password = userCredentials.get(OpenNebulaConstants.PASSWORD);

        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            throw new UnauthenticatedUserException(Messages.Exception.NO_USER_CREDENTIALS);
        }
    }

    /*
     * Using the Opennebula Java Library it is necessary to do some operation in the cloud to check if the
     * user is authenticated. We opted to use UserPool.
     * TODO: check to request directly in the XML-RPC API
     */
    protected boolean isAuthenticated(String openNebulaTokenValue) {
    	Client client;
        UserPool userPool;
        try {
        	client = OpenNebulaClientUtil.createClient(this.endpoint, openNebulaTokenValue);
        	userPool = OpenNebulaClientUtil.getUserPool(client);
        } catch (InternalServerErrorException e) {
            LOGGER.error(Messages.Log.UNEXPECTED, e);
            return false;
        }

        OneResponse info = userPool.info();
        if (info.isError()) {
            LOGGER.error(String.format(Messages.Exception.OPERATION_RETURNED_ERROR_S, info.getMessage()));
            return false;
        }
        return true;
    }

	private boolean isUrlValid(String url) throws FatalErrorException {
		if (url == null || url.trim().isEmpty()) {
			throw new FatalErrorException(
					String.format(Messages.Exception.INVALID_SERVICE_URL_S, (url == null ? "null" : url)));
		}
		return true;
	}
    
}
