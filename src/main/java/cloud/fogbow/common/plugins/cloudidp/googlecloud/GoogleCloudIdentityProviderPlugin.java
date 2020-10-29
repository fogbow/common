package cloud.fogbow.common.plugins.cloudidp.googlecloud;

import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import cloud.fogbow.common.constants.GoogleCloudConstants;
import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.GoogleCloudUser;
import cloud.fogbow.common.plugins.cloudidp.CloudIdentityProviderPlugin;
import cloud.fogbow.common.util.CryptoUtil;
import cloud.fogbow.common.util.connectivity.HttpRequestClient;
import cloud.fogbow.common.util.connectivity.HttpResponse;

public class GoogleCloudIdentityProviderPlugin implements CloudIdentityProviderPlugin<GoogleCloudUser> {

	private final String endPoint;
	private final String TOKEN_ENDPOINT = "/token";
	private final static String URL = "https://oauth2.googleapis.com";
	private final String SCOPE_VALUE = "https://www.googleapis.com/auth/compute";
	private final String GRANT_TYPE_VALUE = "urn:ietf:params:oauth:grant-type:jwt-bearer";

	public GoogleCloudIdentityProviderPlugin(String identityUrl) {
		this.endPoint = identityUrl + TOKEN_ENDPOINT;
	}

	public GoogleCloudIdentityProviderPlugin() {
		this(URL);
	}

	@Override
	public GoogleCloudUser getCloudUser(Map<String, String> userCredentials) throws UnauthenticatedUserException {

		try {

			String email = userCredentials.get(GoogleCloudConstants.Identity.EMAIL_KEY);
			String privateKey = userCredentials.get(GoogleCloudConstants.Identity.PRIVATE_KEY);

			PrivateKey objKey = CryptoUtil.getPrivateKeyFromString(privateKey);

			String signedJwt = this.createJwt(objKey, email);
			Map<String, String> requestBody = new HashMap<String, String>();
			requestBody.put(GoogleCloudConstants.Identity.GRANT_TYPE_KEY, GRANT_TYPE_VALUE);
			requestBody.put(GoogleCloudConstants.Identity.ASSERTION_KEY, signedJwt);

			Map<String, String> header = new HashMap<String, String>();
			header.put(GoogleCloudConstants.Identity.CONTENT_TYPE_KEY, GoogleCloudConstants.Identity.APPLICATION_JSON);

			HttpResponse response = HttpRequestClient.doGenericRequest(HttpMethod.POST, endPoint, header, requestBody);
			CreateAuthenticationResponse responseObject = CreateAuthenticationResponse.fromJson(response.getContent());
			
			return new GoogleCloudUser(email, email, responseObject.getToken(),
					userCredentials.get(GoogleCloudConstants.Identity.PROJECT_ID_KEY));

		} catch (Exception e) {
			throw new UnauthenticatedUserException(e.getMessage());
		}
	}

	private String createJwt(PrivateKey objKey, String email) {

		long now = System.currentTimeMillis();

		Algorithm algorithm = Algorithm.RSA256(null, (RSAPrivateKey) objKey);
		String signedJwt = JWT.create().withIssuer(email).withAudience(endPoint)
				.withClaim(GoogleCloudConstants.Identity.SCOPE_KEY, SCOPE_VALUE).withIssuedAt(new Date(now))
				.withExpiresAt(new Date(now + 3600 * 1000L)).sign(algorithm);

		return signedJwt;
	}
}