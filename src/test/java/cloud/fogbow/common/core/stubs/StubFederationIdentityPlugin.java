package cloud.fogbow.common.core.stubs;

import cloud.fogbow.common.models.FederationUser;
import org.fogbowcloud.ras.core.plugins.aaa.identity.FederationIdentityPlugin;

/**
 * This class is a stub for the FederationIdentityPlugin interface used for tests only.
 * Should not have a proper implementation.
 */
public class StubFederationIdentityPlugin implements FederationIdentityPlugin<FederationUser> {

    public StubFederationIdentityPlugin() {
    }

    @Override
    public FederationUser createToken(String tokenValue) {
        return null;
    }
}
