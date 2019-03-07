package cloud.fogbow.common.util.connectivity.cloud.opennebula;

import cloud.fogbow.common.util.connectivity.FogbowGenericResponse;

public class OpenNebulaResponse extends FogbowGenericResponse {
	private String message;
	private boolean isError;

	public OpenNebulaResponse(String message) {
		// NOTE(pauloewerton): either 'message' or 'errorMessage' should be passed as
		// the response content.
		super(message);
	}

	public OpenNebulaResponse(String message, boolean isError) {
		super(message);
		this.message = message;
		this.isError = isError;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

}
