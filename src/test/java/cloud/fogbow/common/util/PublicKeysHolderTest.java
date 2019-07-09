package cloud.fogbow.common.util;

import cloud.fogbow.common.exceptions.ConfigurationErrorException;
import cloud.fogbow.common.exceptions.FogbowException;
import org.junit.Test;

public class PublicKeysHolderTest {

    private final String malFormedUri = "i^n|e xistent-site/ras";
    private final String wellFormedUri = "http://inexistent-site.lsd.ufcg.edu.br/ras";

    @Test(expected = ConfigurationErrorException.class)
    public void testWithAnInvalidUri() throws FogbowException {
        PublicKeysHolder.getPublicKey(malFormedUri, "8080", "/publicKey");
    }

    @Test(expected = FogbowException.class)
    public void testWithAValidAndUnavailableUri() throws FogbowException {
        String port = "8080";
        String suffix = "/publicKey";
        PublicKeysHolder.getPublicKey(wellFormedUri, port, suffix);
    }
}
