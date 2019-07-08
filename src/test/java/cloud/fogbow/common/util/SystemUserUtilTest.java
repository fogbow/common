package cloud.fogbow.common.util;

import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.SystemUser;
import org.junit.Assert;
import org.junit.Test;

public class SystemUserUtilTest {

    public static final String FAKE_USER_ID = "fakeUserId";
    public static final String FAKE_USER_NAME = "fakeUserName";
    public static final String FAKE_IDENTITY_PROVIDER = "fakeProviderId";

    // Test if a SystemUser is correctly serialized and later deserialized
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

    // Test if size of content is going to overflow the limit a SystemUSer is allowed
    @Test(expected = UnexpectedException.class)
    public void testCreateUserWithTooMuchData() throws UnexpectedException {

        // setup
        String FAKE_USER_NAME = getLongUserName();

        SystemUser systemUser = new SystemUser(FAKE_USER_ID, FAKE_USER_NAME, FAKE_IDENTITY_PROVIDER);

        // exercise/verify
        SystemUserUtil.serialize(systemUser);
    }

    private String getLongUserName() {
        String userName = "";

        for (int i = 0; i < 2 * SystemUserUtil.SERIALIZED_SYSTEM_USER_MAX_SIZE; ++i) {
            userName += "A";
        }

        return userName;
    }

    private SystemUser createSystemUser() {
        return new SystemUser(FAKE_USER_ID, FAKE_USER_NAME, "fakeProviderId");
    }
}
