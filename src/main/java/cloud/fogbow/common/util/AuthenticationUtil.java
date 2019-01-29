package cloud.fogbow.common.util;

import cloud.fogbow.common.constants.FogbowConstants;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.InvalidTokenException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.FederationUser;
import org.apache.commons.lang.StringUtils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationUtil {
    public static FederationUser authenticate(PublicKey asPublicKey, String encryptedTokenValue)
            throws UnauthenticatedUserException, InvalidTokenException {
        try {
            RSAPrivateKey privateKey = ServiceAsymmetricKeysHolder.getInstance().getPrivateKey();
            String plainTokenValue = TokenValueProtector.decrypt(privateKey, encryptedTokenValue,
                    FogbowConstants.TOKEN_STRING_SEPARATOR);
            String[] tokenFields = StringUtils.splitByWholeSeparator(plainTokenValue, FogbowConstants.TOKEN_SEPARATOR);
            String payload = tokenFields[0];
            String signature = tokenFields[1];
            checkIfSignatureIsValid(asPublicKey, payload, signature);
            String[] payloadFields = StringUtils.splitByWholeSeparator(payload, FogbowConstants.PAYLOAD_SEPARATOR);
            String attributesString = payloadFields[0];
            String expirationTime = payloadFields[1];
            checkIfTokenHasNotExprired(expirationTime);
            Map<String, String> attributes = getAttributes(attributesString);
            return new FederationUser(attributes);
        } catch (IOException | GeneralSecurityException e) {
            throw new InvalidTokenException();
        }
    }

    private static void checkIfSignatureIsValid(PublicKey publicKey, String payload, String signature)
            throws UnauthenticatedUserException {

        try {
            if (!RSAUtil.verify(publicKey, payload, signature)) {
                throw new UnauthenticatedUserException(Messages.Exception.INVALID_TOKEN);
            }
        } catch (SignatureException | NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            throw new UnauthenticatedUserException(e.getMessage(), e);
        }
    }

    private static void checkIfTokenHasNotExprired(String expirationTime) throws UnauthenticatedUserException {
        Date currentDate = new Date(getNow());
        Date expirationDate = new Date(Long.parseLong(expirationTime));
        if (expirationDate.before(currentDate)) {
            throw new UnauthenticatedUserException(Messages.Exception.EXPIRED_TOKEN);
        }
    }

    public static Map<String, String> getAttributes(String attributeString) {
        Map<String, String> attributes = new HashMap<>();
        String attributePairs[] = StringUtils.splitByWholeSeparator(attributeString, FogbowConstants.ATTRIBUTE_SEPARATOR);
        for (String pair : attributePairs){
            String[] pairFields = StringUtils.splitByWholeSeparator(pair, FogbowConstants.KEY_VALUE_SEPARATOR);
            String key = pairFields[0];
            String value = pairFields[1];
            attributes.put(key, value);
        }
        return attributes;
    }

    private static long getNow() {
        return System.currentTimeMillis();
    }
}
