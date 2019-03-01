package cloud.fogbow.common.util;

import cloud.fogbow.common.exceptions.FatalErrorException;
import cloud.fogbow.common.stubs.StubClassForFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassFactoryTest {

    private String className;
    ClassFactory classFactory;

    @Before
    public void setUp(){
        classFactory = new ClassFactory();
        className = "cloud.fogbow.common.stubs.StubClassForFactory";
    }

    @Test
    public void createPluginInstanceNoParameters() {
        // exercise
        StubClassForFactory obj = (StubClassForFactory) classFactory.createPluginInstance(className);

        // verify
        assertNotEquals(null, obj);
    }

    @Test
    public void createPluginInstanceOneParameters() {
        // exercise
        String parameter1 = "parameter1";
        StubClassForFactory obj = (StubClassForFactory) classFactory.createPluginInstance(className, parameter1);

        // verify
        assertNotEquals(null, obj);
    }

    @Test
    public void createPluginInstanceTwoParameters() {
        // exercise
        String parameter1 = "parameter1";
        String parameter2 = "parameter2";
        StubClassForFactory obj = (StubClassForFactory) classFactory.createPluginInstance(className, parameter1, parameter2);

        // verify
        assertNotEquals(null, obj);
    }

    @Test(expected = FatalErrorException.class)
    public void createPluginInstanceWithNonExistingClass() {
        String invalidClassPath = "invalid.class.path.InvalidClass";
        classFactory.createPluginInstance(invalidClassPath);
    }
}