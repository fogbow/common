package cloud.fogbow.common.models;

import javax.validation.constraints.NotBlank;

import cloud.fogbow.common.constants.AzureConstants;

public class AzureV1User extends CloudUser {

    public AzureV1User(
            @NotBlank String userId,
            @NotBlank String userName,
            @NotBlank String token) {

        super(userId, userName, token);
    }
    
    public AzureV1User(
            @NotBlank String userId,
            @NotBlank String userName,
            @NotBlank String subscriptionKey,
            @NotBlank String clientKey,
            @NotBlank String accessKey,
            @NotBlank String tenantKey) {

        super(userId, userName);
        String token = subscriptionKey
                + AzureConstants.TOKEN_SEPARATOR
                + clientKey + AzureConstants.TOKEN_SEPARATOR
                + accessKey + AzureConstants.TOKEN_SEPARATOR
                + tenantKey;

        this.setToken(token);
    }

}
