package cloud.fogbow.common.plugins.cloudidp.azure.v1;

import java.util.Map;

import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.models.AzureV1User;
import cloud.fogbow.common.plugins.cloudidp.CloudIdentityProviderPlugin;

public class AzureV1IdentityProviderPlugin implements CloudIdentityProviderPlugin<AzureV1User> {

    @Override
    public AzureV1User getCloudUser(Map<String, String> userCredentials) throws FogbowException {
        // TODO Auto-generated method stub
        return null;
    }

}
