package cloud.fogbow.common.plugins.authorization;

import cloud.fogbow.common.exceptions.ConfigurationErrorException;
import cloud.fogbow.common.exceptions.UnauthorizedRequestException;
import cloud.fogbow.common.models.FogbowOperation;
import cloud.fogbow.common.models.SystemUser;

public interface AuthorizationPlugin<T extends FogbowOperation> {
    /**
     * Verifies if the user represented by the systemUser object is authorized to perform the operation on the
     * type of resource indicated, in the default cloud.
     *
     *
     * @param systemUser the SystemUser object describing the user to be authorized
     * @param operation the Operation object describing the operation the user is requesting to perform
     * @return a boolean stating whether the user is authorized or not.
     */
    public boolean isAuthorized(SystemUser systemUser, T operation) throws UnauthorizedRequestException;

    // TODO documentation
	public void setPolicy(String policy) throws ConfigurationErrorException;

	// TODO documentation
	public void updatePolicy(String policy);
}
