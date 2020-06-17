package cloud.fogbow.common.util.connectivity.cloud.opennebula;

import cloud.fogbow.common.constants.Messages;
import org.apache.log4j.Logger;
import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.OneResponse;
import org.opennebula.client.Pool;
import org.opennebula.client.PoolElement;
import org.opennebula.client.group.GroupPool;
import org.opennebula.client.image.ImagePool;
import org.opennebula.client.secgroup.SecurityGroup;
import org.opennebula.client.template.TemplatePool;
import org.opennebula.client.user.UserPool;
import org.opennebula.client.vm.VirtualMachine;
import org.opennebula.client.vnet.VirtualNetwork;

import cloud.fogbow.common.exceptions.InstanceNotFoundException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.UnauthorizedRequestException;
import cloud.fogbow.common.exceptions.UnexpectedException;

public class OpenNebulaClientUtil {

	private static final Logger LOGGER = Logger.getLogger(OpenNebulaClientUtil.class);
	
	protected static final String RESPONSE_NOT_AUTHORIZED = "Not authorized";
	protected static final String RESPONSE_DONE = "DONE";

	public static Client createClient(String endpoint, String tokenValue) throws UnexpectedException {
		try {
			return new Client(tokenValue, endpoint);
		} catch (ClientConfigurationException e) {
			LOGGER.error(Messages.Log.ERROR_WHILE_CREATING_CLIENT, e);
			throw new UnexpectedException(Messages.Exception.ERROR_WHILE_CREATING_CLIENT);
		}
	}

	public static VirtualMachine getVirtualMachine(Client client, String virtualMachineId)
			throws UnauthorizedRequestException, InstanceNotFoundException, InvalidParameterException {

		VirtualMachine virtualMachine = (VirtualMachine) generateOnePoolElement(client, virtualMachineId, VirtualMachine.class);
		OneResponse response = virtualMachine.info();
		if (response.isError()) {
			String message = response.getErrorMessage();
			LOGGER.error(String.format(Messages.Log.ERROR_MESSAGE_IS_S, message));
			// Not authorized to perform
			if (message.contains(RESPONSE_NOT_AUTHORIZED)) {
				throw new UnauthorizedRequestException();
			}
			// Error getting virtual machine
			throw new InstanceNotFoundException(message);
		} else if (RESPONSE_DONE.equals(virtualMachine.stateStr())) {
			// The instance is not active anymore
			throw new InstanceNotFoundException();
		}
		return virtualMachine;
	}

	public static UserPool getUserPool(Client client) throws UnexpectedException {
		UserPool userpool = (UserPool) generateOnePool(client, UserPool.class);
 		OneResponse response = userpool.info();
 		if (response.isError()) {
 			LOGGER.error(String.format(Messages.Log.ERROR_WHILE_GETTING_USERS_S, response.getErrorMessage()));
			throw new UnexpectedException(String.format(Messages.Exception.ERROR_WHILE_GETTING_USERS_S, response.getErrorMessage()));
 		}
 		LOGGER.info(String.format(Messages.Log.USER_POOL_LENGTH_S, userpool.getLength()));
		return userpool;
	}

	protected static PoolElement generateOnePoolElement(Client client, String poolElementId, Class classType)
			throws InvalidParameterException {
		
		int id;
		try {
			id = Integer.parseInt(poolElementId);
		} catch (Exception e) {
			LOGGER.error(String.format(Messages.Log.ERROR_WHILE_CONVERTING_INSTANCE_ID_S, poolElementId), e);
			throw new InvalidParameterException(String.format(Messages.Exception.ERROR_WHILE_CONVERTING_INSTANCE_ID_S, poolElementId));
		}
		if (classType.isAssignableFrom(SecurityGroup.class)) {
			return new SecurityGroup(id, client);
		} else if (classType.isAssignableFrom(VirtualMachine.class)) {
			return new VirtualMachine(id, client);
		} else if (classType.isAssignableFrom(VirtualNetwork.class)) {
			return new VirtualNetwork(id, client);
		}
		return null;
	}
	
	protected static Pool generateOnePool(Client client, Class classType) {
		if (classType.isAssignableFrom(TemplatePool.class)) {
			return new TemplatePool(client);
		} else if (classType.isAssignableFrom(GroupPool.class)) {
			return new GroupPool(client);
        } else if (classType.isAssignableFrom(ImagePool.class)) {
            return new ImagePool(client);
        } else if (classType.isAssignableFrom(UserPool.class)) {
		    return new UserPool(client);
        }
		return null;
	}
	
}
