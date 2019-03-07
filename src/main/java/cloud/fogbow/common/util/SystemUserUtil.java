package cloud.fogbow.common.util;

import cloud.fogbow.common.constants.FogbowConstants;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.SystemUser;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;

public class SystemUserUtil {
    public static String serialize(SystemUser systemUser) {
        String className = systemUser.getClass().getName();
        String serializedSystemUser = className + FogbowConstants.CLASS_NAME_SEPARATOR + GsonHolder.getInstance().toJson(systemUser);
        return serializedSystemUser;
    }
    
    public static SystemUser deserialize(String serializedSystemUser) throws UnexpectedException {
        String fields[] = StringUtils.splitByWholeSeparator(serializedSystemUser, FogbowConstants.CLASS_NAME_SEPARATOR);
        try {
            Class<?> clazz = Class.forName(fields[0]);
            TypeToken<?> typeToken = TypeToken.get(clazz);
            return GsonHolder.getInstance().fromJson(fields[1], typeToken.getType());
        } catch (ClassNotFoundException e) {
            throw new UnexpectedException(Messages.Exception.UNABLE_TO_FIND_SYSTEM_USER_CLASS_S);
        }
    }
}