package cloud.fogbow.common.plugins.authorization;

import cloud.fogbow.common.util.PluginFactory;

public class AuthorizationPluginInstantiator {
    private static PluginFactory pluginFactory = new PluginFactory();

    public static AuthorizationPlugin getAuthorizationPlugin(String className) {
        return (AuthorizationPlugin) AuthorizationPluginInstantiator.pluginFactory.createPluginInstance(className);
    }
}
