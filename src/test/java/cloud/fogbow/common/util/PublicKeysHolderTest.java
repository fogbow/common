package cloud.fogbow.common.util;

import cloud.fogbow.common.exceptions.ConfigurationErrorException;
import cloud.fogbow.common.exceptions.FogbowException;
import org.junit.Test;

public class PublicKeysHolderTest {

    private final String MALFORMED_URI = "i^n|e xistent-site/ras";
    private final String WELLFORMED_URI = "http://inexistent-site.lsd.ufcg.edu.br/ras";
    private final String PORT = "8080";
    private final String SUFFIX = "/publicKey";

    @Test(expected = ConfigurationErrorException.class)
    public void testWithAnInvalidUri() throws FogbowException {
        PublicKeysHolder.getPublicKey(MALFORMED_URI, PORT, SUFFIX);
    }

    @Test(expected = FogbowException.class)
    public void testWithAValidAndUnavailableUri() throws FogbowException {

        PublicKeysHolder.getPublicKey(WELLFORMED_URI, PORT, SUFFIX);
    }
}
