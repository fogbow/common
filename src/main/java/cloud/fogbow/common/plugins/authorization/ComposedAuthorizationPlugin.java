package cloud.fogbow.common.plugins.authorization;

import cloud.fogbow.common.exceptions.UnauthorizedRequestException;
import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.FatalErrorException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.FogbowOperation;
import cloud.fogbow.common.models.SystemUser;
import cloud.fogbow.common.util.ClassFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComposedAuthorizationPlugin<T extends FogbowOperation> implements AuthorizationPlugin<T> {
    List<AuthorizationPlugin<T>> authorizationPlugins;

    public ComposedAuthorizationPlugin(String confPath) {
        List<String> pluginNames = getPluginNames(confPath);
        this.authorizationPlugins = getPlugins(pluginNames);
    }

    @Override
    public boolean isAuthorized(SystemUser systemUser, FogbowOperation operation)
            throws UnauthorizedRequestException, UnexpectedException {

        for (AuthorizationPlugin plugin : this.authorizationPlugins) {
            if (!plugin.isAuthorized(systemUser, operation)) {
                return false;
            }
        }
        return true;
    }

    private List<String> getPluginNames(String confPath) {
        ArrayList<String> authorizationPluginNames = new ArrayList<>();

        File file = new File(confPath);
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new FatalErrorException(
                    String.format(Messages.Fatal.UNABLE_TO_READ_COMPOSED_AUTHORIZATION_PLUGIN_CONFIGURATION_FILE_S, confPath));
        }

        while (input.hasNextLine()) {
            String nextLine = input.nextLine().trim();
            if (!nextLine.isEmpty()) {
                authorizationPluginNames.add(nextLine);
            }
        }

        return authorizationPluginNames;
    }

    private List<AuthorizationPlugin<T>> getPlugins(List<String> pluginNames) {
        ClassFactory classFactory = new ClassFactory();
        ArrayList<AuthorizationPlugin<T>> authorizationPlugins = new ArrayList<>();
        for (int i = 0; i < pluginNames.size(); i++) {
            authorizationPlugins.add(i, (AuthorizationPlugin<T>) classFactory.createPluginInstance(pluginNames.get(i)));
        }
        return authorizationPlugins;
    }
}
