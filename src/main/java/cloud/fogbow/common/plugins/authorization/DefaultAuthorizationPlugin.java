package cloud.fogbow.common.plugins.authorization;

import cloud.fogbow.common.exceptions.UnauthorizedRequestException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.FederationUser;

public class DefaultAuthorizationPlugin implements AuthorizationPlugin {

    public DefaultAuthorizationPlugin() {
    }

    @Override
    public boolean isAuthorized(FederationUser federationUserToken, String cloudName, String operation, String type)
            throws UnauthorizedRequestException, UnexpectedException {
        return true;
    }

    @Override
    public boolean isAuthorized(FederationUser federationUserToken, String operation, String type)
            throws UnauthorizedRequestException, UnexpectedException {
        return true;
    }
}
