package cloud.fogbow.common.core.stubs;

import cloud.fogbow.common.core.models.instances.NetworkInstance;
import cloud.fogbow.common.core.models.orders.NetworkOrder;
import org.fogbowcloud.ras.core.models.tokens.Token;
import cloud.fogbow.common.core.plugins.interoperability.NetworkPlugin;

/**
 * This class is a stub for the NetworkPlugin interface used for tests only.
 * Should not have a proper implementation.
 */
public class StubNetworkPlugin implements NetworkPlugin<Token> {

    public StubNetworkPlugin(String confFilePath) {
    }

    @Override
    public String requestInstance(NetworkOrder networkOrder, Token token) {
        return null;
    }

    @Override
    public NetworkInstance getInstance(String networkInstanceId, Token token) {
        return null;
    }

    @Override
    public void deleteInstance(String networkInstanceId, Token token) {
    }
}
