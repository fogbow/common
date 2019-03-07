package cloud.fogbow.common.plugins.authorization;

import cloud.fogbow.common.exceptions.UnauthorizedRequestException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.SystemUser;

public class AuthorizationController {
    private AuthorizationPlugin authorizationPlugin;

    public AuthorizationController(AuthorizationPlugin authorizationPlugin) {
        this.authorizationPlugin = authorizationPlugin;
    }

    public void authorize(SystemUser systemUser, String cloudName, String operation, String resourceType)
            throws UnexpectedException, UnauthorizedRequestException {
        this.authorizationPlugin.isAuthorized(systemUser, cloudName, operation, resourceType);
    }

    public void authorize(SystemUser systemUser, String operation, String resourceType)
            throws UnexpectedException, UnauthorizedRequestException {
        this.authorizationPlugin.isAuthorized(systemUser, operation, resourceType);
    }
}
