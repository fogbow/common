package cloud.fogbow.common.constants;

public class AzureConstants {

    // azure resource name
    public static final int MAXIMUM_RESOURCE_NAME_LENGTH = 30;
    
    // azure tags
    public static final String TAG_NAME = "Name";
    
    // azure properties keys
    public static final String DEFAULT_REGION_NAME_KEY = "default_region_name";
    public static final String DEFAULT_RESOURCE_GROUP_NAME_KEY = "default_resource_group_name";
    public static final String DEFAULT_VIRTUAL_NETWORK_NAME_KEY = "default_virtual_network_name";

    
    // azure credentials
    public enum Credential {
        CLIENT_ID_KEY("client_id"),
        TENANT_ID_KEY("tenant_id"),
        CLIENT_KEY("client_key"),
        SUBSCRIPTION_ID_KEY("subscription_id");

        private String value;

        Credential(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

}
