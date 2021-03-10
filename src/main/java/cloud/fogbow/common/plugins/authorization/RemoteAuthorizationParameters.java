package cloud.fogbow.common.plugins.authorization;

import java.util.Map;

public interface RemoteAuthorizationParameters {
	Map<String, String> asRequestBody();
}
