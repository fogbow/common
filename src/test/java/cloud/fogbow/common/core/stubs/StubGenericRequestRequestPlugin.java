package cloud.fogbow.common.core.stubs;

import cloud.fogbow.common.core.plugins.interoperability.genericrequest.GenericRequest;
import cloud.fogbow.common.core.plugins.interoperability.genericrequest.GenericRequestPlugin;
import cloud.fogbow.common.core.plugins.interoperability.genericrequest.GenericRequestResponse;

public class StubGenericRequestRequestPlugin implements GenericRequestPlugin<Token> {

    public StubGenericRequestRequestPlugin() {
    }

    @Override
    public GenericRequestResponse redirectGenericRequest(GenericRequest genericRequest, Token token) {
        return null;
    }

}
