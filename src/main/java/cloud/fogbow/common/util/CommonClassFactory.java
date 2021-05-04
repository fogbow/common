package cloud.fogbow.common.util;

import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.FatalErrorException;

import java.lang.reflect.Constructor;
import java.util.Arrays;

// Each package has to have its own ClassFactory

public class CommonClassFactory implements ClassFactory {

    public Object createPluginInstance(String pluginClassName, String ... params) throws FatalErrorException {

        Object pluginInstance;
        Constructor<?> constructor;

        try {
            Class<?> classpath = Class.forName(pluginClassName);
            if (params.length > 0) {
                Class<String>[] constructorArgTypes = new Class[params.length];
                Arrays.fill(constructorArgTypes, String.class);
                constructor = classpath.getConstructor(constructorArgTypes);
            } else {
                constructor = classpath.getConstructor();
            }

            pluginInstance = constructor.newInstance(params);
        } catch (ClassNotFoundException e) {
            throw new FatalErrorException(String.format(Messages.Exception.UNABLE_TO_FIND_CLASS_S, pluginClassName));
        } catch (Exception e) {
            throw new FatalErrorException(e.getMessage(), e);
        }
        return pluginInstance;
    }

    public Object createPluginInstance(String pluginClassName, Object ... params)
            throws FatalErrorException {

        Object pluginInstance;
        Constructor<?> constructor;

        try {
            Class<?> classpath = Class.forName(pluginClassName);
            if (params.length > 0) {
                Class<?>[] constructorArgTypes = new Class[params.length];

                for (int i = 0; i < params.length; i++) {
                    constructorArgTypes[i] = params[i].getClass();
                }
                
                constructor = classpath.getConstructor(constructorArgTypes);
            } else {
                constructor = classpath.getConstructor();
            }

            pluginInstance = constructor.newInstance(params);
        } catch (ClassNotFoundException e) {
            throw new FatalErrorException(String.format(Messages.Exception.UNABLE_TO_FIND_CLASS_S, pluginClassName));
        } catch (Exception e) {
            throw new FatalErrorException(e.getMessage(), e);
        }
        return pluginInstance;
    }
}
