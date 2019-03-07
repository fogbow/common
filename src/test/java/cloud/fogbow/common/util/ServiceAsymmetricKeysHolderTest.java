package cloud.fogbow.common.util;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.Assert.*;

public class ServiceAsymmetricKeysHolderTest {

    private ServiceAsymmetricKeysHolder keysHolder;
    private String pubKeyPath;
    private String privKeyPath;

    @Before
    public void setUp() throws Exception {
        this.keysHolder = ServiceAsymmetricKeysHolder.getInstance();

        String keysPath = HomeDir.getPath();
        pubKeyPath = keysPath + "public.key";
        privKeyPath = keysPath + "private.key";

        this.keysHolder.setPublicKeyFilePath(pubKeyPath);
        this.keysHolder.setPrivateKeyFilePath(privKeyPath);
    }

    @Test
    public void getPublicKey() throws IOException, GeneralSecurityException {
        //setup
        RSAPublicKey expectedPublicKey = CryptoUtil.getPublicKey(pubKeyPath);

        // exercise
        RSAPublicKey publicKey = this.keysHolder.getPublicKey();

        // verify
        assertEquals(expectedPublicKey, publicKey);
    }

    @Test
    public void getPrivateKey() throws IOException, GeneralSecurityException {
        //setup
        RSAPrivateKey expectedPrivateKey = CryptoUtil.getPrivateKey(privKeyPath);

        // exercise
        RSAPrivateKey privateKey = this.keysHolder.getPrivateKey();

        // verify
        assertEquals(expectedPrivateKey, privateKey);
    }
}