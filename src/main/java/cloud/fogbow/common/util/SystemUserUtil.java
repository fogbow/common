package cloud.fogbow.common.util;

import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.SystemUser;

public class SystemUserUtil {

    public static final int SERIALIZED_SYSTEM_USER_MAX_SIZE = 2048;

    public static String serialize(SystemUser systemUser) throws UnexpectedException {
        SerializedEntityHolder<SystemUser> serializedSystemUserHolder = new SerializedEntityHolder<SystemUser>(systemUser);
        String serializedSystemUser = serializedSystemUserHolder.toString();

        if(serializedSystemUser.length() > SystemUserUtil.SERIALIZED_SYSTEM_USER_MAX_SIZE) {
            throw new UnexpectedException(Messages.Exception.MAXIMUM_SIZE_EXCEEDED);
        }

        return serializedSystemUser;
    }
    
    public static SystemUser deserialize(String serializedSystemUser) throws UnexpectedException {
        try {
            SerializedEntityHolder<SystemUser> serializedSystemUserHolder = GsonHolder.getInstance().fromJson(serializedSystemUser, SerializedEntityHolder.class);
            SystemUser systemUser = serializedSystemUserHolder.getSerializedEntity();
            return systemUser;
        } catch (ClassNotFoundException e) {
            throw new UnexpectedException(Messages.Exception.UNABLE_TO_FIND_SYSTEM_USER_CLASS_S);
        }
    }
}