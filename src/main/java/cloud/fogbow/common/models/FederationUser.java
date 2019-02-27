package cloud.fogbow.common.models;

import cloud.fogbow.common.constants.FogbowConstants;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.UnexpectedException;

import java.util.Map;
import java.util.Objects;

public class FederationUser {
    private String tokenProviderId;
    private String userId;
    private String userName;
    private String tokenValue;
    private Map<String, String> extraAttributes;

    public FederationUser(String tokenProviderId, String userId, String userName, String tokenValue, Map<String, String> extraAttributes) {
        this.tokenProviderId = tokenProviderId;
        this.userId = userId;
        this.userName = userName;
        this.tokenValue = tokenValue;
        this.extraAttributes = extraAttributes;
    }

    public String getExtraAttribute(String attributeKey) throws UnexpectedException {
        if (this.extraAttributes == null) throw new UnexpectedException(Messages.Exception.INVALID_TOKEN);
        return this.extraAttributes.get(attributeKey);
    }

    public String getTokenProviderId() {
        return tokenProviderId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public Map<String, String> getExtraAttributes() {
        return extraAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FederationUser that = (FederationUser) o;
        return Objects.equals(this.getUserId(), that.getUserId()) &&
                Objects.equals(this.getTokenProviderId(), that.getTokenProviderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExtraAttributes());
    }
}
