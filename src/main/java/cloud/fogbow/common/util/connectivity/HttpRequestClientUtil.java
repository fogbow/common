package cloud.fogbow.common.util.connectivity;

import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.util.GsonHolder;
import cloud.fogbow.common.util.HttpErrorToFogbowExceptionMapper;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestClientUtil {
    private static final Logger LOGGER = Logger.getLogger(HttpRequestClientUtil.class);

    public static HttpResponse doGenericRequest(HttpMethod method, String urlString,
                                                HashMap<String, String> headers, HashMap<String, String> body)
                   throws FogbowException {
        int responseCode = -1;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method.getName());

            addHeadersIntoConnection(connection, headers);

            if (!body.isEmpty()) {
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                os.write(toByteArray(body));
                os.flush();
                os.close();
            }

            responseCode = connection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer responseBuffer = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
            in.close();

            Map<String, List<String>> responseHeaders = connection.getHeaderFields();
            return new HttpResponse(responseBuffer.toString(), responseCode, responseHeaders);
        } catch (ProtocolException | MalformedURLException e) {
            throw new InvalidParameterException(e.getMessage(), e);
        } catch (IOException e) {
            throw HttpErrorToFogbowExceptionMapper.map(responseCode, e.getMessage());
        }
    }

    private static void addHeadersIntoConnection(HttpURLConnection connection, Map<String, String> headers) {
        for (String key : headers.keySet()) {
            connection.setRequestProperty(key, headers.get(key));
        }
    }

    private static byte[] toByteArray(Map<String, String> body) {
        String json = GsonHolder.getInstance().toJson(body, Map.class);
        return json.getBytes();
    }
}
