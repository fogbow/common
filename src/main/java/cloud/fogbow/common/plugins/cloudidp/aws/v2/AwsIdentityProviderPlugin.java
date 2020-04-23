package cloud.fogbow.common.plugins.cloudidp.aws.v2;

import cloud.fogbow.common.constants.AwsConstants;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.AwsV2User;
import cloud.fogbow.common.plugins.cloudidp.CloudIdentityProviderPlugin;
import org.apache.log4j.Logger;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.iam.IamClient;
import java.util.Map;

public class AwsIdentityProviderPlugin implements CloudIdentityProviderPlugin<AwsV2User> {

    private static final Logger LOGGER = Logger.getLogger(AwsIdentityProviderPlugin.class);

    public AwsV2User getCloudUser(Map<String, String> userCredentials) throws FogbowException {

        String accessKey = userCredentials.get(AwsConstants.ACCESS_KEY);
        String secretAccessKey = userCredentials.get(AwsConstants.SECRET_ACCESS_KEY);

        checkCredentials(accessKey, secretAccessKey);
        String userId = authenticate(accessKey, secretAccessKey);
        return new AwsV2User(userId, userId, accessKey, secretAccessKey);
    }

    private void checkCredentials(String accessKey, String secretAccessKey) throws InvalidParameterException{
        if(accessKey == null || secretAccessKey == null) {
            throw new InvalidParameterException(Messages.Exception.NO_USER_CREDENTIALS);
        }
    }

    protected String authenticate(String accessKey, String secretAccessKey) throws UnauthenticatedUserException{
        try {
            AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretAccessKey);
            StaticCredentialsProvider awsProvider = StaticCredentialsProvider.create(awsCreds);
            IamClient client = IamClient.builder()
            		.credentialsProvider(awsProvider)
            		.region(Region.AWS_GLOBAL)
            		.build();
            
            return client.getUser().user().userId();
        } catch (Exception e) {
            LOGGER.error(Messages.Exception.AUTHENTICATION_ERROR, e);
            throw new UnauthenticatedUserException(e.getMessage());
        }
    }
}
