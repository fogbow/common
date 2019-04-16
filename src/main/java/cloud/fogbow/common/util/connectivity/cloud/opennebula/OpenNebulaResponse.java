package cloud.fogbow.common.util.connectivity.cloud.opennebula;

import cloud.fogbow.common.util.connectivity.FogbowGenericResponse;

public class OpenNebulaResponse extends FogbowGenericResponse {

	private boolean isError;

	public OpenNebulaResponse(String message) {
		// NOTE(pauloewerton): either 'message' or 'errorMessage' should be passed as
		// the response content.
		super(message);
	}

	public OpenNebulaResponse(String message, boolean isError) {
		super(message);
		this.isError = isError;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

}
