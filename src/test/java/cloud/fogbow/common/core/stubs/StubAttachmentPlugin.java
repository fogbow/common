package cloud.fogbow.common.core.stubs;

import cloud.fogbow.common.core.models.instances.AttachmentInstance;
import cloud.fogbow.common.core.models.orders.AttachmentOrder;
import org.fogbowcloud.ras.core.models.tokens.Token;
import cloud.fogbow.common.core.plugins.interoperability.AttachmentPlugin;

/**
 * This class is a stub for the AttachmentPlugin interface used for tests only.
 * Should not have a proper implementation.
 */
public class StubAttachmentPlugin implements AttachmentPlugin<Token> {

    public StubAttachmentPlugin(String confFilePath) {
    }

    @Override
    public String requestInstance(AttachmentOrder attachmentOrder, Token token) {
        return null;
    }

    @Override
    public void deleteInstance(String attachmentInstanceId, Token token) {
    }

    @Override
    public AttachmentInstance getInstance(String attachmentInstanceId, Token token) {
        return null;
    }
}
