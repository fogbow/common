package cloud.fogbow.common.core.stubs;

import cloud.fogbow.common.core.models.instances.VolumeInstance;
import cloud.fogbow.common.core.models.orders.VolumeOrder;
import org.fogbowcloud.ras.core.models.tokens.Token;
import cloud.fogbow.common.core.plugins.interoperability.VolumePlugin;

/**
 * This class is a stub for the VolumePlugin interface used for tests only.
 * Should not have a proper implementation.
 */
public class StubVolumePlugin implements VolumePlugin<Token> {

    public StubVolumePlugin(String confFilePath) {
    }

    @Override
    public String requestInstance(VolumeOrder volumeOrder, Token token) {
        return null;
    }

    @Override
    public VolumeInstance getInstance(String volumeInstanceId, Token token) {
        return null;
    }

    @Override
    public void deleteInstance(String volumeInstanceId, Token token) {
    }
}
