package cloud.fogbow.common.constants;

public class AzureConstants {

    public enum Credential {
        CLIENT_ID_KEY("client_id"),
        TENANT_ID_KEY("tenant_id"),
        CLIENT_KEY("client_key"),
        SUBSCRIPTION_ID_KEY("subscription_id"),
        RESOURCE_GROUP_NAME_KEY("resource_group_name"),
        REGION_NAME_KEY("region_name");

        private String value;

        Credential(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

}
