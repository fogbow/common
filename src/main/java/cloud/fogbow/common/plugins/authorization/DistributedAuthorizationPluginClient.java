package cloud.fogbow.common.plugins.authorization;

import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.UnauthorizedRequestException;
import cloud.fogbow.common.models.RestfulFogbowOperation;
import cloud.fogbow.common.models.SystemUser;
import cloud.fogbow.common.plugins.cloudidp.cloudstack.v4_9.CloudStackIdentityProviderPlugin;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class DistributedAuthorizationPluginClient implements AuthorizationPlugin<RestfulFogbowOperation> {
    private static final Logger LOGGER = Logger.getLogger(DistributedAuthorizationPluginClient.class);

    public String serverUrl;

    public DistributedAuthorizationPluginClient() {
    }

    @Override
    public boolean isAuthorized(SystemUser systemUserToken, RestfulFogbowOperation operation) {
        String endpoint = operation.getEndpoint();
        StringBuffer content = null;

        try {
            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            con.disconnect();
            in.close();
        } catch (Exception e) {
            LOGGER.error(String.format(Messages.Log.ERROR_MESSAGE_IS_S, e.getMessage()), e);
        }
        return Boolean.valueOf(content.toString());
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
