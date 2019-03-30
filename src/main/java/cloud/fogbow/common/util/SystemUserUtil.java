package cloud.fogbow.common.util;

import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.SystemUser;

public class SystemUserUtil {

    public static final int SERIALIZED_SYSTEM_USER_MAX_SIZE = 2048;

    public static String serialize(SystemUser systemUser) throws UnexpectedException{
        SerializableEntity<SystemUser> serializableSystemUser = new SerializableEntity<SystemUser>(systemUser);
        String serializedSystemUser = serializableSystemUser.toString();

        if(serializedSystemUser.length() > SystemUserUtil.SERIALIZED_SYSTEM_USER_MAX_SIZE) {
            throw new UnexpectedException(Messages.Exception.MAXIMUM_SIZE_EXCEEDED);
        }

        return serializedSystemUser;
    }
    
    public static SystemUser deserialize(String serializedSystemUser) throws UnexpectedException {
        try {
            SerializableEntity<SystemUser> serializableEntity = GsonHolder.getInstance().fromJson(serializedSystemUser, SerializableEntity.class);
            SystemUser systemUser = serializableEntity.getSerializedEntity();
            return systemUser;
        } catch (ClassNotFoundException e) {
            throw new UnexpectedException(Messages.Exception.UNABLE_TO_FIND_SYSTEM_USER_CLASS_S);
        }
    }
}