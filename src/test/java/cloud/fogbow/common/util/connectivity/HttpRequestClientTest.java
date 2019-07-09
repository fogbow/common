package cloud.fogbow.common.util;

import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.util.connectivity.HttpRequestClient;
import org.junit.Test;

import java.util.HashMap;

public class HttpRequestClientTest {

    @Test(expected = FogbowException.class)
    public void testGenericRequestWithWellFormedAndInexistentUrl() throws FogbowException {
        String endpoint = "http://www.inexistentendpoint.com";
        HttpRequestClient.doGenericRequest(HttpMethod.GET, endpoint, new HashMap<>(), new HashMap<>());
    }
}
