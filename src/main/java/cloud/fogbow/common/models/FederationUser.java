package cloud.fogbow.common.models;

import cloud.fogbow.common.constants.FogbowConstants;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.UnexpectedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FederationUser {
    private Map<String, String> attributes;

    public FederationUser(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getAttribute(String attributeKey) throws UnexpectedException {
        if (this.attributes == null) throw new UnexpectedException(Messages.Exception.INVALID_TOKEN);
        return this.attributes.get(attributeKey);
    }

    public String getTokenProvider() throws UnexpectedException {
        return getAttribute(FogbowConstants.PROVIDER_ID_KEY);
    }

    public String getUserId() throws UnexpectedException {
        return getAttribute(FogbowConstants.USER_ID_KEY);
    }

    public String getUserName() throws UnexpectedException {
        return getAttribute(FogbowConstants.USER_NAME_KEY);
    }

    public String getTokenValue() throws UnexpectedException {
        return getAttribute(FogbowConstants.TOKEN_VALUE_KEY);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FederationUser that = (FederationUser) o;
        Map<String, String> thisAttributes = new HashMap(getAttributes());
        Map<String, String> thatAttributes = new HashMap(that.getAttributes());
        thisAttributes.remove(FogbowConstants.TOKEN_VALUE_KEY);
        thatAttributes.remove(FogbowConstants.TOKEN_VALUE_KEY);
        return Objects.equals(thisAttributes, thatAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttributes());
    }
}
