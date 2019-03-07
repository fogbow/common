package cloud.fogbow.common.plugins.cloudidp.opennebula;

import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.constants.OpenNebulaConstants;
import cloud.fogbow.common.exceptions.*;
import cloud.fogbow.common.models.OpenNebulaUser;
import cloud.fogbow.common.plugins.cloudidp.CloudIdentityProviderPlugin;
import cloud.fogbow.common.util.connectivity.cloud.opennebula.OpenNebulaClientFactory;
import org.apache.log4j.Logger;
import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.user.UserPool;

import java.util.Map;

public class OpenNebulaIdentityProviderPlugin implements CloudIdentityProviderPlugin<OpenNebulaUser> {
    private static final Logger LOGGER = Logger.getLogger(OpenNebulaIdentityProviderPlugin.class);

    private OpenNebulaClientFactory factory;

    public OpenNebulaIdentityProviderPlugin() throws FatalErrorException {
        //TODO. fix me.
        this.factory = new OpenNebulaClientFactory(null);
    }

    public OpenNebulaIdentityProviderPlugin(OpenNebulaClientFactory factory, String tokenProviderId) {
        this.factory = factory;
    }

    /*
     * The userId is the same as the userName, because the userName is unique in Opennebula
     */
    @Override
    public OpenNebulaUser getCloudUser(Map<String, String> userCredentials) throws FogbowException {
        if (userCredentials == null) {
            throw new InvalidParameterException(Messages.Exception.NO_USER_CREDENTIALS);
        }

        String username = userCredentials.get(OpenNebulaConstants.USERNAME);
        String password = userCredentials.get(OpenNebulaConstants.PASSWORD);

        if (username == null || password == null) {
            throw new InvalidParameterException(Messages.Exception.NO_USER_CREDENTIALS);
        }

        String openNebulaTokenValue = username + OpenNebulaConstants.TOKEN_VALUE_SEPARATOR + password;
        if (!isAuthenticated(openNebulaTokenValue)) {
            LOGGER.error(Messages.Exception.AUTHENTICATION_ERROR);
            throw new UnauthenticatedUserException();
        }
        return new OpenNebulaUser(username, username, openNebulaTokenValue);
    }

    /*
     * Using the Opennebula Java Library it is necessary to do some operation in the cloud to check if the
     * user is authenticated. We opted to use UserPool.
     * TODO: check to request directly in the XML-RPC API
     */
    protected boolean isAuthenticated(String openNebulaTokenValue) {
        final Client client;
        final UserPool userPool;
        try {
            client = this.factory.createClient(openNebulaTokenValue);
            userPool = this.factory.createUserPool(client);
        } catch (UnexpectedException e) {
            LOGGER.error(Messages.Exception.UNEXPECTED, e);
            return false;
        }

        OneResponse info = userPool.info();
        if (info.isError()) {
            LOGGER.error(String.format(
                    Messages.Exception.OPERATION_RETURNED_ERROR_S, info.getMessage()));
            return false;
        }
        return true;
    }

    protected void setFactory(OpenNebulaClientFactory factory) {
        this.factory = factory;
    }

    protected OpenNebulaClientFactory getFactory() {
        return factory;
    }
}
