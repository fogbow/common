package cloud.fogbow.common.util.connectivity;

import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.util.GsonHolder;
import org.apache.http.Header;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestClientUtil {
    private static final Logger LOGGER = Logger.getLogger(HttpRequestClientUtil.class);

    public GenericRequestHttpResponse doGenericRequest(HttpMethod method, String urlString,
                                                       HashMap<String, String> headers, HashMap<String, String> body)
                   throws FogbowException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method.name());

            addHeadersIntoConnection(connection, headers);

            if (!body.isEmpty()) {
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                os.write(toByteArray(body));
                os.flush();
                os.close();
            }

            int responseCode = connection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            StringBuffer responseBuffer = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
            in.close();

            Map<String, List<String>> responseHeaders = connection.getHeaderFields();
            return new GenericRequestHttpResponse(responseBuffer.toString(), responseCode, responseHeaders);
        } catch (Exception e) {
            throw new FogbowException(e.getMessage(), e);
        }
    }

    private void addHeadersIntoConnection(HttpURLConnection connection, Map<String, String> headers) {
        for (String key : headers.keySet()) {
            connection.setRequestProperty(key, headers.get(key));
        }
    }

    private byte[] toByteArray(Map<String, String> body) {
        String json = GsonHolder.getInstance().toJson(body, Map.class);
        return json.getBytes();
    }

    public class Response {

        private String content;
        private Header[] headers;

        public Response(String content, Header[] headers) {
            this.content = content;
            this.headers = headers;
        }

        public String getContent() {
            return content;
        }

        public Header[] getHeaders() {
            return headers;
        }
    }
}
