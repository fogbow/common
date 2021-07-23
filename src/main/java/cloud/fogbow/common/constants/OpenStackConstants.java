package cloud.fogbow.common.constants;

public class OpenStackConstants {
    public static final String X_SUBJECT_TOKEN = "X-Subject-Token";
    public static final String X_AUTH_TOKEN_KEY = "X-Auth-Token";
    public static final String NETWORK_PORTS_RESOURCE = "Network Ports";
    public static final String SECURITY_GROUP_RESOURCE = "Security Group";
    public static final String PUBLIC_IP_RESOURCE = "Public IP";
    public static final String IPV4_ETHER_TYPE = "IPv4";
    public static final String IPV6_ETHER_TYPE = "IPv6";
    public static final String INGRESS_DIRECTION = "ingress";
    public static final String TCP_PROTOCOL = "tcp";
    public static final String UDP_PROTOCOL = "udp";
    public static final String ICMP_PROTOCOL = "icmp";
    public static final String ACTION = "action";
    public static final String ENDPOINT_SEPARATOR = "/";
    public static final String QUERY_NAME = "?name=";
    public static final String ACTIVE_STATE = "active";
    public static final String PUBLIC_VISIBILITY = "public";
    public static final String PRIVATE_VISIBILITY = "private";
    public static final String QUERY_ACTIVE_IMAGES = "?status=" + ACTIVE_STATE;
    public static final String NOVA_V2_API_ENDPOINT = "/v2";
    public static final String NEUTRON_V2_API_ENDPOINT = "/v2.0";
    public static final String CINDER_V2_API_ENDPOINT = "/v2";
    public static final String CINDER_V3_API_ENDPOINT = "/v3";
    public static final String GLANCE_V2_API_ENDPOINT = "/v2";
    public static final String SERVERS_ENDPOINT = "/servers";
    public static final String VOLUMES_ENDPOINT = "/volumes";
    public static final String TYPES_ENDPOINT = "/types";
    public static final String SECURITY_GROUP_RULES_ENDPOINT = "/security-group-rules";
    public static final String SECURITY_GROUPS_ENDPOINT = "/security-groups";
    public static final String LIMITS_ENDPOINT = "/limits";
    public static final String QUOTAS_ENDPOINT = "/quotas";
    public static final String SUFFIX_ENDPOINT = "/details.json";
    public static final String FLOATINGIPS_ENDPOINT = "/floatingips";
    public static final String PORTS_ENDPOINT = "/ports";
    public static final String NETWORK_ENDPOINT = "/networks";
    public static final String SUBNET_ENDPOINT = "/subnets";
    public static final String IMAGE_ENDPOINT = "images";
    public static final String KEYPAIRS_ENDPOINT = "/os-keypairs";
    public static final String FLAVORS_ENDPOINT = "/flavors";
    public static final String EXTRA_SPECS_ENDPOINT = "/os-extra_specs";
    public static final String OS_VOLUME_ATTACHMENTS = "/os-volume_attachments";

    public static class Volume {
        public static final String VOLUME_KEY_JSON = "volume";
        public static final String STATUS_KEY_JSON = "status";
        public static final String SIZE_KEY_JSON = "size";
        public static final String NAME_KEY_JSON = "name";
        public static final String ID_KEY_JSON = "id";
        public static final String VOLUME_TYPES_KEY_JSON = "volume_types";
        public static final String EXTRA_SPECS_KEY_JSON = "extra_specs";
    }

    public static class Attachment {
        public static final String VOLUME_ATTACHMENT_KEY_JSON = "volumeAttachment";
        public static final String VOLUME_ID_KEY_JSON = "volumeId";
        public static final String SERVER_ID_KEY_JSON = "serverId";
        public static final String ID_KEY_JSON = "id";
        public static final String DEVICE_KEY_JSON = "device";
    }

    public static class Compute {
        public static final String SERVER_KEY_JSON = "server";
        public static final String ID_KEY_JSON = "id";
        public static final String NAME_KEY_JSON = "name";
        public static final String DISK_KEY_JSON = "disk";
        public static final String MEMORY_KEY_JSON = "ram";
        public static final String VCPUS_KEY_JSON = "vcpus";
        public static final String ADDRESSES_KEY_JSON = "addresses";
        public static final String FLAVOR_KEY_JSON = "flavor";
        public static final String STATUS_KEY_JSON = "status";
        public static final String FAULT_MSG_KEY_JSON = "fault.message";
        public static final String ADDRESS_KEY_JSON = "addr";
        public static final String IMAGE_REFERENCE_KEY_JSON = "imageRef";
        public static final String FLAVOR_REFERENCE_KEY_JSON = "flavorRef";
        public static final String FLAVOR_EXTRA_SPECS_KEY_JSON = "extra_specs";
        public static final String USER_DATA_KEY_JSON = "user_data";
        public static final String KEY_NAME_KEY_JSON = "key_name";
        public static final String NETWORKS_KEY_JSON = "networks";
        public static final String SECURITY_GROUPS_KEY_JSON = "security_groups";
        public static final String UUID_KEY_JSON = "uuid";
        public static final String KEY_PAIR_KEY_JSON = "keypair";
        public static final String PUBLIC_KEY_KEY_JSON = "public_key";
        public static final String ADD_SECURITY_GROUP_KEY_JSON = "addSecurityGroup";
        public static final String REMOVE_SECURITY_GROUP_KEY_JSON = "removeSecurityGroup";
        public static final String PAUSE_KEY_JSON = "pause";
        public static final String UNPAUSE_KEY_JSON = "unpause";
        public static final String SUSPEND_KEY_JSON = "suspend";
        public static final String RESUME_KEY_JSON = "resume";
        public static final String SHELVE_KEY_JSON = "shelve";
        public static final String UNSHELVE_KEY_JSON = "unshelve";
        public static final String CREATE_IMAGE_KEY_JSON = "createImage";
    }

    public static class Network {
        public static final String NETWORK_KEY_JSON = "network";
        public static final String NAME_KEY_JSON = "name";
        public static final String PROJECT_ID_KEY_JSON = "project_id";
        public static final String ID_KEY_JSON = "id";
        public static final String NETWORK_ID_KEY_JSON = "network_id";
        public static final String IP_VERSION_KEY_JSON = "ip_version";
        public static final String GATEWAY_IP_KEY_JSON = "gateway_ip";
        public static final String CIDR_KEY_JSON = "cidr";
        public static final String ENABLE_DHCP_KEY_JSON = "enable_dhcp";
        public static final String DNS_NAMESERVERS_KEY_JSON = "dns_nameservers";
        public static final String SECURITY_GROUP_KEY_JSON = "security_group";
        public static final String SECURITY_GROUPS_KEY_JSON = "security_groups";
        public static final String SECURITY_GROUP_RULE_KEY_JSON = "security_group_rule";
        public static final String SECURITY_GROUP_RULES_KEY_JSON = "security_group_rules";
        public static final String DIRECTION_KEY_JSON = "direction";
        public static final String SECURITY_GROUP_ID_KEY_JSON = "security_group_id";
        public static final String REMOTE_IP_PREFIX_KEY_JSON = "remote_ip_prefix";
        public static final String PROTOCOL_KEY_JSON = "protocol";
        public static final String MIN_PORT_KEY_JSON = "port_range_min";
        public static final String MAX_PORT_KEY_JSON = "port_range_max";
        public static final String PROVIDER_SEGMENTATION_ID_KEY_JSON = "provider:segmentation_id";
        public static final String SUBNET_KEY_JSON = "subnet";
        public static final String SUBNETS_KEY_JSON = "subnets";
        public static final String STATUS_KEY_JSON = "status";
        public static final String ETHER_TYPE_KEY_JSON = "ethertype";
    }

    public static class Image {
        public static final String ID_KEY_JSON = "id";
        public static final String NAME_KEY_JSON = "name";
        public static final String SIZE_KEY_JSON = "size";
        public static final String STATUS_KEY_JSON = "status";
        public static final String MIN_RAM_KEY_JSON = "min_ram";
        public static final String MIN_DISK_KEY_JSON = "min_disk";
        public static final String VISIBILITY_KEY_JSON = "visibility";
        public static final String OWNER_KEY_JSON = "owner";
        public static final String NEXT_KEY_JSON = "next";
        public static final String IMAGES_KEY_JSON = "images";
    }

    public static class Identity {
        public static final String V3_TOKENS_ENDPOINT_PATH = "/auth/tokens";
        public static final String ID_KEY_JSON = "id";
        public static final String AUTH_KEY_JSON = "auth";
        public static final String USER_KEY_JSON = "user";
        public static final String USER_NAME_KEY_JSON = "username";
        public static final String NAME_KEY_JSON = "name";
        public static final String SCOPE_KEY_JSON = "scope";
        public static final String DOMAIN_KEY_JSON = "domain";
        public static final String TOKEN_KEY_JSON = "token";
        public static final String METHODS_KEY_JSON = "methods";
        public static final String PROJECT_KEY_JSON = "project";
        public static final String PROJECT_NAME_KEY_JSON = "projectname";
        public static final String PASSWORD_KEY_JSON = "password";
        public static final String IDENTITY_KEY_JSON = "identity";
    }

    public static class Quota {
        public static final String LIMITS_KEY_JSON = "limits";
        public static final String ABSOLUTE_KEY_JSON = "absolute";
        public static final String MAX_TOTAL_CORES_KEY_JSON = "maxTotalCores";
        public static final String MAX_TOTAL_RAM_SIZE_KEY_JSON = "maxTotalRAMSize";
        public static final String MAX_TOTAL_INSTANCES_KEY_JSON = "maxTotalInstances";
        public static final String TOTAL_CORES_USED_KEY_JSON = "totalCoresUsed";
        public static final String TOTAL_RAM_USED_KEY_JSON = "totalRAMUsed";
        public static final String TOTAL_INSTANCES_USED_KEY_JSON = "totalInstancesUsed";
        public static final String MAX_TOTAL_VOLUME_GIGABYTES_KEY_JSON = "maxTotalVolumeGigabytes";
        public static final String MAX_TOTAL_VOLUMES_KEY_JSON = "maxTotalVolumes";
        public static final String TOTAL_VOLUMES_USED_KEY_JSON = "totalVolumesUsed";
        public static final String TOTAL_GIGABYTES_USED_KEY_JSON = "totalGigabytesUsed";
        public static final String QUOTA_KEY_JSON = "quota";
        public static final String FLOATING_IP_KEY_JSON = "floatingip";
        public static final String NETWORK_KEY_JSON = "network";
        public static final String LIMIT_KEY_JSON = "limit";
        public static final String RESERVED_KEY_JSON = "reserved";
        public static final String USED_KEY_JSON = "used";
    }

    public static class PublicIp {
        public static final String ID_KEY_JSON = "id";
        public static final String STATUS_KEY_JSON = "status";
        public static final String FLOATING_IP_KEY_JSON = "floatingip";
        public static final String FLOATING_IP_ADDRESS_KEY_JSON = "floating_ip_address";
        public static final String FLOATING_NETWORK_ID_KEY_JSON = "floating_network_id";
        public static final String PORT_ID_KEY_JSON = "port_id";
        public static final String PROJECT_ID_KEY_JSON = "project_id";
        public static final String PORTS_KEY_JSON = "ports";
    }

    public static class SecurityRule {
        public static final String SECURITY_GROUP_ID_KEY_JSON = "security_group_id";
        public static final String NAME_KEY_JSON = "name";
    }
}
