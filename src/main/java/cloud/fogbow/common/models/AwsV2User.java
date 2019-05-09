package cloud.fogbow.common.models;

import cloud.fogbow.common.constants.AwsConstants;

public class AwsV2User extends CloudUser{
    private String accessKey;
    private String secretAccessKey;
    private String token;

    public AwsV2User(String userId, String userName, String accessKey, String secretAccessKey) {
        super(userId, userName, "");
        String awsToken = accessKey + AwsConstants.TOKEN_VALUE_SEPARATOR + secretAccessKey;
        this.setToken(awsToken);
    }

    public AwsV2User(String userId, String userName, String token) {
        super(userId, userName, token);
    }

}
