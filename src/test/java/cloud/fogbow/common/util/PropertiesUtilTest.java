package cloud.fogbow.common.util;

import cloud.fogbow.common.exceptions.FatalErrorException;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PropertiesUtilTest {

    private static final String FAKE_PROPERTIES_FILE_NAME = "fake-properties.conf";
    private static final String NON_EXISTENT_FILE_NAME = "non-existent-file-name.conf";

    // test if the loadProperties returns the expected properties when a
    // correct filepath is passed
    @Test
    public void testLoadPropertiesSuccessfully() {
        // setup
        String filename = HomeDir.getPath() + FAKE_PROPERTIES_FILE_NAME;

        // exercise
        Properties props = PropertiesUtil.loadProperties(filename);

        // verify
        assertEquals(props.getProperty("fake_property1"), "f1");
        assertEquals(props.getProperty("fake_property2"), "f2");
        assertEquals(props.getProperty("fake_property3"), "f3");
    }

    // test if a FatalErrorException is thrown when a non existent filepath is passed to loadProperties
    @Test(expected = FatalErrorException.class) // verify
    public void testLoadPropertiesWithANonExistentFile() {
        // setup // exercise
        PropertiesUtil.loadProperties(NON_EXISTENT_FILE_NAME);
    }


    // Test if it can correctly get properties form a file
    @Test
    public void testReadPropertiesFromSingleFile() {

        // set up
        String fakePropertiesPath = HomeDir.getPath() + FAKE_PROPERTIES_FILE_NAME;
        Properties props = PropertiesUtil.readProperties(fakePropertiesPath);

        // exercise/verify

        assertEquals(props.getProperty("fake_property1"), "f1");
        assertEquals(props.getProperty("fake_property2"), "f2");
        assertEquals(props.getProperty("fake_property3"), "f3");
    }

    // Try to get properties from non-existing file
    @Test(expected = FatalErrorException.class)
    public void testGetPropertiesFromNonExisting() {
        PropertiesUtil.readProperties(NON_EXISTENT_FILE_NAME);
    }
}