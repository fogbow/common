package cloud.fogbow.common.util;

import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.InternalServerErrorException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class ServiceAsymmetricKeysHolder {
	private static final Logger LOGGER = Logger.getLogger(ServiceAsymmetricKeysHolder.class);

	private RSAPublicKey servicePublicKey;
	private RSAPrivateKey servicePrivateKey;
	private String publicKeyFilePath;
	private String privateKeyFilePath;
	private static ServiceAsymmetricKeysHolder instance;
	
    public static synchronized ServiceAsymmetricKeysHolder getInstance() {
        if (instance == null) {
            instance = new ServiceAsymmetricKeysHolder();
        }
        return instance;
    }

    public void setPublicKeyFilePath(String publicKeyFilePath) {
        this.publicKeyFilePath = publicKeyFilePath;
    }

    public void setPrivateKeyFilePath(String privateKeyFilePath) {
        this.privateKeyFilePath = privateKeyFilePath;
    }

    public RSAPublicKey getPublicKey() throws InternalServerErrorException {
	    if (this.servicePublicKey == null) {
	        if (this.publicKeyFilePath == null) throw new InternalServerErrorException(Messages.Exception.NO_PUBLIC_KEY_DEFINED);
            try {
                this.servicePublicKey = CryptoUtil.getPublicKey(this.publicKeyFilePath);
            } catch (IOException | GeneralSecurityException e) {
                throw new InternalServerErrorException(Messages.Exception.INVALID_PUBLIC_KEY);
            }
        }
	    return this.servicePublicKey;
    }

    public RSAPrivateKey getPrivateKey() throws InternalServerErrorException {
        if (this.servicePrivateKey == null) {
            if (this.privateKeyFilePath == null) throw new InternalServerErrorException(Messages.Exception.NO_PRIVATE_KEY_DEFINED);
            try {
                this.servicePrivateKey = CryptoUtil.getPrivateKey(this.privateKeyFilePath);
            } catch (IOException | GeneralSecurityException e) {
                throw new InternalServerErrorException(Messages.Exception.INVALID_PRIVATE_KEY);
            }
        }
        return this.servicePrivateKey;
    }
    
    public static void reset(String publicKeyFilePath, String privateKeyFilePath) { 
        instance = null;
        getInstance();
        instance.setPublicKeyFilePath(publicKeyFilePath);
        instance.setPrivateKeyFilePath(privateKeyFilePath);
    }
}
