package cloud.fogbow.common.util;

import cloud.fogbow.common.exceptions.FatalErrorException;
import cloud.fogbow.common.stubs.StubClassForFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PluginFactoryTest {

    private String className;
    PluginFactory pluginFactory;

    @Before
    public void setUp(){
        pluginFactory = new PluginFactory();
        className = "cloud.fogbow.common.stubs.StubClassForFactory";
    }

    @Test
    public void createPluginInstanceNoParameters() {
        // exercise
        StubClassForFactory obj = (StubClassForFactory) pluginFactory.createPluginInstance(className);

        // verify
        assertNotEquals(null, obj);
    }

    @Test
    public void createPluginInstanceOneParameters() {
        // exercise
        String parameter1 = "parameter1";
        StubClassForFactory obj = (StubClassForFactory) pluginFactory.createPluginInstance(className, parameter1);

        // verify
        assertNotEquals(null, obj);
    }

    @Test
    public void createPluginInstanceTwoParameters() {
        // exercise
        String parameter1 = "parameter1";
        String parameter2 = "parameter2";
        StubClassForFactory obj = (StubClassForFactory) pluginFactory.createPluginInstance(className, parameter1, parameter2);

        // verify
        assertNotEquals(null, obj);
    }

    @Test(expected = FatalErrorException.class)
    public void createPluginInstanceWithNonExistingClass() {
        String invalidClassPath = "invalid.class.path.InvalidClass";
        pluginFactory.createPluginInstance(invalidClassPath);
    }
}