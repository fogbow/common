package cloud.fogbow.common.plugins.cloudidp.googlecloud;

import com.google.gson.annotations.SerializedName;


import cloud.fogbow.common.util.GsonHolder;

/**
 * Documentation: https://developer.openstack.org/api-ref/identity/v3/
 * <p>
 * Response Example:
{
    "access_token": "ya29.c.Ko8B4AeKjQGMOcMUqY2aiWJL0pKY_O1TK9Kjl",
    "expires_in": 3599,
    "token_type": "Bearer"
}
 * <p>
 * We use the @SerializedName annotation to specify that the request parameter is not equal to the class field.
 */

public class CreateAuthenticationResponse {
	
	@SerializedName("access_token")	
	private String token;
	@SerializedName("expires_in")	
	private long expiresIn;
	@SerializedName("token_type")	
	private String tokenType;

	public static CreateAuthenticationResponse fromJson(String json) {
        return GsonHolder.getInstance().fromJson(json, CreateAuthenticationResponse.class);
    }

	public String getToken() {
		return token;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public String getTokenType() {
		return tokenType;
	}
}

