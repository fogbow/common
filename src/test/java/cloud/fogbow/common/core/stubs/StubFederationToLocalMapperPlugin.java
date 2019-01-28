package cloud.fogbow.common.core.stubs;

import cloud.fogbow.common.models.FederationUser;
import org.fogbowcloud.ras.core.models.tokens.Token;
import cloud.fogbow.common.core.plugins.mapper.FederationToLocalMapperPlugin;

/**
 * This class is a stub for the FederationToLocalMapperPlugin interface used for tests only.
 * Should not have a proper implementation.
 */
public class StubFederationToLocalMapperPlugin implements FederationToLocalMapperPlugin {

    public StubFederationToLocalMapperPlugin(String conf1, String conf2) {
    }

    @Override
    public Token map(FederationUser user) {
        return null;
    }
}
