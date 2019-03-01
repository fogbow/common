package cloud.fogbow.common.util.cloud.cloudstack;

import java.security.InvalidParameterException;

import static cloud.fogbow.common.constants.CloudStackConstants.PublicIp.JOB_ID_KEY_JSON;
import static cloud.fogbow.common.constants.CloudStackConstants.PublicIp.QUERY_ASYNC_JOB_RESULT;

public class QueryAsyncJobResultRequest extends CloudStackRequest {

	protected QueryAsyncJobResultRequest(Builder builder) throws InvalidParameterException {
		addParameter(JOB_ID_KEY_JSON, builder.jobId);
	}

    @Override
    public String toString() {
        return super.toString();
    }
	
	@Override
	public String getCommand() {
		return QUERY_ASYNC_JOB_RESULT;
	}

    public static class Builder {
        private String jobId;

        public Builder jobId(String jobId) {
            this.jobId = jobId;
            return this;
        }

        public QueryAsyncJobResultRequest build() throws InvalidParameterException {
            return new QueryAsyncJobResultRequest(this);
        }
    }
	
}
