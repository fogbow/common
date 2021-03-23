package cloud.fogbow.common.plugins.authorization;

import java.util.Map;

import cloud.fogbow.common.exceptions.FogbowException;

public interface RemoteAuthorizationParameters {
	Map<String, String> asRequestBody() throws FogbowException;
}
