package cloud.fogbow.common.util;

import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.SystemUser;
import org.junit.Assert;
import org.junit.Test;

public class SystemUserUtilTest {
    // Test if a SystemUser is correctly seralized and later deserialized
    @Test
    public void testSerializationDeserealizationOfSystemUserObject() throws UnexpectedException {
        // Setup
        SystemUser systemUser = createSystemUser();

        // Exercise
        String serializedUser = SystemUserUtil.serialize(systemUser);
        SystemUser recoveredSystemUser = SystemUserUtil.deserialize(serializedUser);

        // Verify
        Assert.assertEquals(systemUser, recoveredSystemUser);
    }

    private SystemUser createSystemUser() {
        return new SystemUser("fakeUserId", "fakeUserName", "fakeProviderId");
    }
}
