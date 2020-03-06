package cloud.fogbow.common.constants;

public class AzureConstants {

    // azure resources id
    public static final String VIRTUAL_MACHINE_STRUCTURE = "/subscriptions/%s/resourceGroups/%s/providers/Microsoft.Compute/virtualMachines/%s";
    public static final String NETWORK_INTERFACE_STRUCTURE = "/subscriptions/%s/resourceGroups/%s/providers/Microsoft.Network/networkInterfaces/%s";
    public static final String BIGGER_STRUCTURE = NETWORK_INTERFACE_STRUCTURE;
    public static final String VIRTUAL_MACHINE_ID_PREFIX = "vm-";
    public static final String VIRTUAL_NETWORK_ID_PREFIX = "vnet-";
    public static final String VOLUME_ID_PREFIX = "vol-";
    public static final String PUBLIC_IP_ID_PREFIX = "pip-";
    
    // azure resource name
    public static final String RESOURCE_NAME_SEPARATOR = "_";
    
    // azure properties keys
    public static final String DEFAULT_NETWORK_INTERFACE_NAME_KEY = "default_network_interface_name";
    public static final String DEFAULT_REGION_NAME_KEY = "default_region_name";
    public static final String DEFAULT_RESOURCE_GROUP_NAME_KEY = "default_resource_group_name";
    
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
