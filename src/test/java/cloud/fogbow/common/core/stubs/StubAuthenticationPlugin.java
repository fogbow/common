package cloud.fogbow.common.core.stubs;

import cloud.fogbow.common.models.FederationUser;
import org.fogbowcloud.ras.core.plugins.aaa.authentication.AuthenticationPlugin;

/**
 * This class is a stub for the AuthenticationPlugin interface used for tests only.
 * Should not have a proper implementation.
 */
public class StubAuthenticationPlugin implements AuthenticationPlugin<FederationUser> {

    public StubAuthenticationPlugin() {
    }

    @Override
    public boolean isAuthentic(String requestingMember, FederationUser federationToken) {
        return false;
    }

}
