package cloud.fogbow.common.util;

import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.ConfigurationErrorException;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.UnavailableProviderException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.util.connectivity.HttpRequestClient;
import cloud.fogbow.common.util.connectivity.HttpResponse;
import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

public class PublicKeysHolder {
    public static  RSAPublicKey getPublicKey(String serviceAddress, String servicePort, String suffix) throws FogbowException {
        RSAPublicKey publicKey = null;

        URI uri = null;
        try {
            uri = new URI(serviceAddress);
        } catch (URISyntaxException e) {
            throw new ConfigurationErrorException(String.format(Messages.Exception.INVALID_URL, serviceAddress));
        }
        uri = UriComponentsBuilder.fromUri(uri).port(servicePort).path(suffix).build(true).toUri();

        String endpoint = uri.toString();
        HttpResponse response = HttpRequestClient.doGenericRequest(HttpMethod.GET, endpoint, new HashMap<>(), new HashMap<>());
        if (response.getHttpCode() > HttpStatus.SC_OK) {
            Throwable e = new HttpResponseException(response.getHttpCode(), response.getContent());
            throw new UnavailableProviderException(e.getMessage(), e);
        } else {
            try {
                Gson gson = new Gson();
                Map<String, String> jsonResponse = gson.fromJson(response.getContent(), HashMap.class);
                //TODO: the key should be a constant defined elsewhere; this class is a candidate to go to common
                String publicKeyString = jsonResponse.get("publicKey");
                publicKey = CryptoUtil.getPublicKeyFromString(publicKeyString);
            } catch (GeneralSecurityException e) {
                throw new UnexpectedException(Messages.Exception.INVALID_PUBLIC_KEY);
            }
            return publicKey;
        }
    }
}
