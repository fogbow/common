package cloud.fogbow.common.constants;

public class GoogleCloudConstants {
    public static final String AUTHORIZATION_KEY = "Authorization";
    public static final String BEARER_S = "Bearer %s";
    public static final String ENDPOINT_SEPARATOR = "/";

    public static class Network {
        public static final String FIREWALL_ENDPOINT = "/global/firewalls";
        public static final String FIREWALL_CIDR_INCOME_JSON = "sourceRanges";
        public static final String FIREWALL_CIDR_OUTCOME_JSON = "destinationRanges";
        public static final String FIREWALL_ALLOWED_JSON = "allowed";
        public static final String FIREWALL_ALLOWED_PORT_JSON = "port";
        public static final String FIREWALL_ALLOWED_IP_PROTOCOL_JSON = "IPProtocol";
        public static final String FIREWALL_CONNECTION_DIRECTION_JSON = "direction";
        public static final String FIREWALL_SECURITY_RULES_JSON = "items";
        public static final String ID_KEY_JSON = "id";
        public static final String FIREWALL_NAME_JSON = "name";
        public static final String FIREWALL_ID_JSON = "id";
        public static final String FIREWALL_NETWORK_JSON = "id";
        public static final String FIREWALL_ETHER_TYPE_JSON = "ethertype";
    }
}
