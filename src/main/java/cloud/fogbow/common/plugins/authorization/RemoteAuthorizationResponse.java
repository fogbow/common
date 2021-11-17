package cloud.fogbow.common.plugins.authorization;

import java.util.HashMap;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;

import cloud.fogbow.common.util.connectivity.HttpResponse;

public class RemoteAuthorizationResponse {
	private static final String AUTHORIZATION_RESPONSE_AUTHORIZED_FIELD = "authorized";
	private boolean authorized;
	
	// Maybe refactor this into a factory in the future
	@VisibleForTesting
	static RemoteAuthorizationResponse getRemoteAuthorizationResponse(HttpResponse response) {
		return new RemoteAuthorizationResponse(response);
	}
	
	public RemoteAuthorizationResponse() {
		
	}
	
	public RemoteAuthorizationResponse(HttpResponse response) {
		Gson gson = new Gson();
		
		this.authorized = (boolean) gson.fromJson(response.getContent(), HashMap.class).
				get(AUTHORIZATION_RESPONSE_AUTHORIZED_FIELD);
	}
	
	public RemoteAuthorizationResponse(boolean authorized) {
		this.authorized = authorized;
	}
	
	public boolean getAuthorized() {
		return this.authorized;
	}
	
    @Override
    public boolean equals(Object o) {
    	if (o == null || !(o instanceof RemoteAuthorizationResponse)) {
    		return false;
    	}
    	
    	RemoteAuthorizationResponse castResponse = (RemoteAuthorizationResponse) o;

    	return castResponse.authorized == this.authorized;
    }
}
