package cloud.fogbow.common.util;

import cloud.fogbow.common.exceptions.FatalErrorException;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

public class PropertiesUtilTest {

    private static final String FAKE_PROPERTIES_FILE_NAME = "fake-properties.conf";
    private static final String NO_PERMISSION_FILE_NAME = "no-permission.conf";
    private static final String NON_EXISTENT_FILE_NAME = "non-existent-file-name.conf";

    // Test if it can correctly get properties form a file
    @Test
    public void testGetProperties() {

        // set up
        String fakePropertiesPath = HomeDir.getPath() + FAKE_PROPERTIES_FILE_NAME;
        Properties fakeProperties = PropertiesUtil.readProperties(fakePropertiesPath);

        // exercise/verify

        for (int i = 1; i <= 3; i++){
            String expectedFakePropertyKey = "fake_property" + i;
            String expectedFakePropertyValue = "f" + i;

            String fakeProperty = fakeProperties.getProperty(expectedFakePropertyKey);

            assertEquals(expectedFakePropertyValue, fakeProperty);
        }
        System.out.println(fakePropertiesPath);
    }

    // Try to get properties from a file non-allowed file
    @Test(expected = FatalErrorException.class)
    public void testGetPropertiesFromNonAllowed() {
        String nonExistingPropertiesPath = HomeDir.getPath() + NO_PERMISSION_FILE_NAME;
        Properties fakeProperties = PropertiesUtil.readProperties(nonExistingPropertiesPath);
    }

    // Try to get properties from non-existing file
    @Test(expected = FatalErrorException.class)
    public void testGetPropertiesFromNonExisting() {
        Properties fakeProperties = PropertiesUtil.readProperties(NON_EXISTENT_FILE_NAME);
    }

    @Test
    public void testLoadProperties() {

    }
}