package cloud.fogbow.common.plugins.authorization;

import cloud.fogbow.common.exceptions.UnauthorizedRequestException;
import cloud.fogbow.common.models.RestfulFogbowOperation;
import cloud.fogbow.common.models.SystemUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class DistributedAuthorizationPluginClient implements AuthorizationPlugin<RestfulFogbowOperation> {
    public static final String AUTH_ENDPOINT = "/auth";

    public String serverUrl;

    public DistributedAuthorizationPluginClient() {
    }

    @Override
    public boolean isAuthorized(SystemUser systemUserToken, RestfulFogbowOperation operation) throws UnauthorizedRequestException {
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
            e.printStackTrace();
        }
        return Boolean.valueOf(content.toString());
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
