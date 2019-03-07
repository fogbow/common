package cloud.fogbow.common.models;

import java.util.Objects;

public class SystemUser extends User {
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
}
