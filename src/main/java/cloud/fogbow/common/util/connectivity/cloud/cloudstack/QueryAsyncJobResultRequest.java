package cloud.fogbow.common.util.connectivity.cloud.cloudstack;

import cloud.fogbow.common.exceptions.InternalServerErrorException;
import cloud.fogbow.common.exceptions.InvalidParameterException;

import static cloud.fogbow.common.constants.CloudStackConstants.PublicIp.JOB_ID_KEY_JSON;
import static cloud.fogbow.common.constants.CloudStackConstants.PublicIp.QUERY_ASYNC_JOB_RESULT;

public class QueryAsyncJobResultRequest extends CloudStackRequest {

	protected QueryAsyncJobResultRequest(Builder builder) throws InternalServerErrorException {
	    super(builder.cloudStackUrl);
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
        private String cloudStackUrl;
        private String jobId;

        public Builder jobId(String jobId) {
            this.jobId = jobId;
            return this;
        }

        public QueryAsyncJobResultRequest build(String cloudStackUrl) throws InternalServerErrorException {
            this.cloudStackUrl = cloudStackUrl;
            return new QueryAsyncJobResultRequest(this);
        }
    }
	
}
