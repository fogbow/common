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

    /**
     * Replaces the plugin instance policy by the given policy.
     * 
     * @param policy the policy to replace.
     * @throws ConfigurationErrorException if the policy is not valid or some other
     * error occurs.
     */
	public void setPolicy(String policy) throws ConfigurationErrorException;

	/**
	 * Updates the plugin instance policy, using given policy as reference. This operation
     * is expected to add rules if they do not exist, update if they exist and remove if required.
     * 
	 * @param policy the policy used to update.
	 * @throws ConfigurationErrorException if the policy is not valid or some other
	 * error occurs.
	 */
	public void updatePolicy(String policy) throws ConfigurationErrorException;
}
