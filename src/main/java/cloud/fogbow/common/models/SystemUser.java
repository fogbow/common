package cloud.fogbow.common.models;

import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.util.GsonHolder;
import cloud.fogbow.common.util.SerializedEntityHolder;

import java.util.Objects;

public class SystemUser extends User {

    public static final int SERIALIZED_SYSTEM_USER_MAX_SIZE = 2048;

    private String identityProviderId;

    public SystemUser(String userId, String userName, String identityProviderId) {
        super(userId, userName);
        this.identityProviderId = identityProviderId;
    }

    public String getIdentityProviderId() {
        return identityProviderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemUser that = (SystemUser) o;
        return Objects.equals(this.getId(), that.getId()) &&
                Objects.equals(this.getIdentityProviderId(), that.getIdentityProviderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(identityProviderId, getId(), getName());
    }

    public static String serialize(SystemUser systemUser) throws UnexpectedException {
        SerializedEntityHolder<SystemUser> serializedSystemUserHolder = new SerializedEntityHolder<SystemUser>(systemUser);
        String serializedSystemUser = serializedSystemUserHolder.toString();

        if(serializedSystemUser.length() > SystemUser.SERIALIZED_SYSTEM_USER_MAX_SIZE) {
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
            throw new UnexpectedException(Messages.Exception.UNABLE_TO_FIND_SYSTEM_USER_CLASS);
        }
    }
}
