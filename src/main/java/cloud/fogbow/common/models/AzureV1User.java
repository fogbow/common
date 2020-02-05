package cloud.fogbow.common.models;

import cloud.fogbow.common.constants.AzureConstants;

public class AzureV1User extends CloudUser {

    public AzureV1User(String userId, String userName, String token) {
        super(userId, userName, token);
    }
    
    public AzureV1User(String userId, String userName, String subscriptionKey, String clientKey, String accessKey, String tenantKey) {
        super(userId, userName);
        String token = subscriptionKey + AzureConstants.TOKEN_VALUE_SEPARATOR
                + clientKey + AzureConstants.TOKEN_VALUE_SEPARATOR
                + accessKey + AzureConstants.TOKEN_VALUE_SEPARATOR
                + tenantKey;
        
        this.setToken(token);
    }

}
