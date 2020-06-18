package cloud.fogbow.common.plugins.cloudidp;

import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.CloudUser;

import java.util.Map;

public interface CloudIdentityProviderPlugin<T extends CloudUser> {
    /**
     * Authenticates the user using the provided credentials and in case of success returns a representation of
     * the authenticated user.
     *
     * @param userCredentials a map containing the credentials to authenticate the user with the cloud IdP service.
     * @return a CloudUser object that represents the authenticated user and can be used to access the cloud.
     */
    public T getCloudUser(Map<String, String> userCredentials) throws UnauthenticatedUserException;
}
