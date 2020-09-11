package cloud.fogbow.common.util;

import cloud.fogbow.common.exceptions.ConfigurationErrorException;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InternalServerErrorException;
import org.junit.Test;

public class PublicKeysHolderTest {

    private final String MALFORMED_URI = "i^n|e xistent-site/ras";
    private final String WELLFORMED_URI = "http://inexistent-site.lsd.ufcg.edu.br/ras";
    private final String PORT = "8080";
    private final String SUFFIX = "/publicKey";

    // test case: When calling getPublicKey method with an invalid URI, it must
    // throw a InternalServerErrorException.
    @Test(expected = InternalServerErrorException.class) // verify
    public void testWithAnInvalidUri() throws FogbowException {
        // exercise
        PublicKeysHolder.getPublicKey(MALFORMED_URI, PORT, SUFFIX);
    }

    // test case: When calling the getPublicKey method with a valid but unavailable
    // URI, it must throw a FogbowException.
    @Test(expected = FogbowException.class) // verify
    public void testWithAValidAndUnavailableUri() throws FogbowException {
        // exercise
        PublicKeysHolder.getPublicKey(WELLFORMED_URI, PORT, SUFFIX);
    }
}
