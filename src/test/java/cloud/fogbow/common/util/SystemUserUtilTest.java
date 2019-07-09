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

    @Test(expected = UnexpectedException.class)
    public void testSerializeWhenSizeBiggerThanMaxSize() throws UnexpectedException {
        String largeName = "largename";
        for(int i = 0; i < 20; i++) {
            largeName += largeName;
        }

        SystemUser sysUser = new SystemUser("fakeId", largeName, "fakeProvider");

        String serialized = SystemUserUtil.serialize(sysUser);
    }

    private SystemUser createSystemUser() {
        return new SystemUser("fakeUserId", "fakeUserName", "fakeProviderId");
    }
}
