package cloud.fogbow.common.core.stubs;

import cloud.fogbow.common.core.models.Operation;
import cloud.fogbow.common.core.models.ResourceType;
import cloud.fogbow.common.models.FederationUser;
import org.fogbowcloud.ras.core.plugins.aaa.authorization.AuthorizationPlugin;

/**
 * This class is a stub for the AuthorizationPlugin interface used for tests only.
 * Should not have a proper implementation.
 */
public class StubAuthorizationPlugin implements AuthorizationPlugin {

    public StubAuthorizationPlugin() {
    }

    @Override
    public boolean isAuthorized(FederationUser federationUser, String cloudName, Operation operation, ResourceType type) {
        return true;
    }

    @Override
    public boolean isAuthorized(FederationUser federationUser, Operation operation, ResourceType type) {
        return true;
    }
}
