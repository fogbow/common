package cloud.fogbow.common.util;

import cloud.fogbow.common.exceptions.FatalErrorException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;

import static org.junit.Assert.*;

public class PropertiesUtilTest {

    private static final String FAKE_PROPERTIES_FILE_NAME = "fake-properties.conf";
    private static final String NO_PERMISSION_FILE_NAME = "no-permission.conf";
    private static final String NON_EXISTENT_FILE_NAME = "non-existent-file-name.conf";

    // Test if it can correctly get properties form a file
    @Test
    public void testReadPropertiesFromSingleFile() {

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
    }

    @Test
    public void testReadPropertiesFromManyFile() {

        // setup
        String[] fakePropertiesFileNames = {"fake-properties-1.conf", "fake-properties-2.conf"};

        List<String> fakePropertiesFileList = new ArrayList<>();

        for (int i = 0; i < fakePropertiesFileNames.length; ++i) {
            fakePropertiesFileList.add(HomeDir.getPath() + fakePropertiesFileNames[i]);
        }

        Properties fakeProperties = PropertiesUtil.readProperties(fakePropertiesFileList);

        // exercise/verify

        for (int i = 1; i <= 6; i++){
            String expectedFakePropertyKey = "fake_property" + i;
            String expectedFakePropertyValue = "f" + i;

            String fakeProperty = fakeProperties.getProperty(expectedFakePropertyKey);

            assertEquals(expectedFakePropertyValue, fakeProperty);
        }
    }

    // Try to get properties from a file non-allowed file
    @Test(expected = FatalErrorException.class)
    public void testGetPropertiesFromNonAllowed() throws IOException {
        String nonExistingPropertiesPath = HomeDir.getPath() + NO_PERMISSION_FILE_NAME;

        File file = new File(nonExistingPropertiesPath);
        file.createNewFile();

        Set<PosixFilePermission> perms = new HashSet<>();

        Files.setPosixFilePermissions(file.toPath(), perms);

        Properties fakeProperties = PropertiesUtil.readProperties(nonExistingPropertiesPath);
    }

    // Try to get properties from non-existing file
    @Test(expected = FatalErrorException.class)
    public void testGetPropertiesFromNonExisting() {
        Properties fakeProperties = PropertiesUtil.readProperties(NON_EXISTENT_FILE_NAME);
    }
}