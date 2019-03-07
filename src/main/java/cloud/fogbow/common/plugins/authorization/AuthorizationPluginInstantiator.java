package cloud.fogbow.common.plugins.authorization;

import cloud.fogbow.common.util.ClassFactory;

public class AuthorizationPluginInstantiator {
    private static ClassFactory classFactory = new ClassFactory();

    public static AuthorizationPlugin getAuthorizationPlugin(String className) {
        return (AuthorizationPlugin) AuthorizationPluginInstantiator.classFactory.createPluginInstance(className);
    }
}
