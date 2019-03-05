package cloud.fogbow.common.util.cloud.cloudstack;

import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.models.CloudUser;
import org.apache.http.client.HttpResponseException;

public class CloudStackQueryJobResult {

    public static final int PROCESSING = 0;
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;

    public static String getQueryJobResult(CloudStackHttpClient client, String jobId, CloudUser cloudStackToken)
            throws FogbowException {
        QueryAsyncJobResultRequest queryAsyncJobResultRequest = new QueryAsyncJobResultRequest.Builder()
                .jobId(jobId)
                .build();

        CloudStackUrlUtil
                .sign(queryAsyncJobResultRequest.getUriBuilder(), cloudStackToken.getToken());

        String jsonResponse = null;
        String requestUrl = queryAsyncJobResultRequest.getUriBuilder().toString();

        try {
            jsonResponse = client.doGetRequest(requestUrl, cloudStackToken);
        } catch (HttpResponseException e) {
            CloudStackHttpToFogbowExceptionMapper.map(e);
        }

        return jsonResponse;
    }
}
