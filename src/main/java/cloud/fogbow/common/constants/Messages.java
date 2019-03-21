package cloud.fogbow.common.constants;

public class Messages {
    public static class Exception {
        public static final String ATTEMPTING_TO_ADD_A_NULL_ITEM = "Attempting to add a null item.";
        public static final String ATTEMPTING_TO_REMOVE_A_NULL_ITEM = "Attempting to remove a null item.";
        public static final String AUTHENTICATION_ERROR = "Authentication error.";
        public static final String AUTHORIZATION_ERROR = "Authorization error.";
        public static final String CLASS_S_SHOULD_BE_CLONEABLE = "Class %s should be cloneable";
        public static final String CONFIGURATION_ERROR = "There's an error in configuration files.";
        public static final String FATAL_ERROR = "Fatal error.";
        public static final String FOGBOW = "Fogbow exception.";
        public static final String INSTANCE_NOT_FOUND = "Instance not found.";
        public static final String INVALID_CREDENTIALS = "Invalid credentials.";
        public static final String INVALID_CHAR_C_FOR_RANDOM_KEY_S_AT_INDEX_D = "Invalid char \"%c\" for random key: \"%s\" at index %d";
        public static final String INVALID_PARAMETER = "Invalid parameter.";
        public static final String INVALID_PUBLIC_KEY = "Invalid public key fetched from external server.";
        public static final String INVALID_TOKEN = "Invalid token value.";
        public static final String INVALID_URL = "Please check the url: %s.";
        public static final String NEITHER_BODY_OR_HEADERS_CAN_BE_NULL = "Neither body or headers can be null";
        public static final String NO_AVAILABLE_RESOURCES = "No available resources.";
        public static final String NO_USER_CREDENTIALS = "No user credentials given.";
        public static final String OPERATION_RETURNED_ERROR_S = "Operation returned error: %s";
        public static final String QUOTA_EXCEEDED = "Quota exceeded.";
        public static final String REMOTE_COMMUNICATION = "Error while sending message to remote RAS.";
        public static final String UNABLE_TO_FIND_SYSTEM_USER_CLASS_S = "Unable to find system user class %s.";
        public static final String UNAVAILABLE_PROVIDER = "Provider is not available.";
        public static final String UNEXPECTED = "Unexpected error.";
        public static final String WRONG_SYNTAX_FOR_ENDPOINT_S = "Wrong syntax for endpoint %s.";
    }

    public static class Fatal {
        public static final String INVALID_SERVICE_URL_S = "Invalid service URL: %s.";
        public static final String PROPERTY_FILE_S_NOT_FOUND = "Property file %s not found.";
        public static final String UNABLE_TO_FIND_CLASS_S = "Unable to find class %s.";
        public static final String UNABLE_TO_READ_COMPOSED_AUTHORIZATION_PLUGIN_CONFIGURATION_FILE_S =
                "Unable to read ComposedAuthorizationPlugin configuration file %s.";
    }

    public static final class Error {
        public static final String ERROR_MESSAGE_IS_S = "Error message is: %s.";
        public static final String ERROR_WHILE_CONVERTING_INSTANCE_ID = "Error while converting instanceid %s to integer.";
        public static final String ERROR_WHILE_CREATING_CLIENT = "Error while creating client.";
        public static final String ERROR_WHILE_CREATING_IMAGE_FROM_TEMPLATE_S = "Error while creating image from template: %s.";
        public static final String ERROR_WHILE_CREATING_NETWORK_FROM_TEMPLATE_S = "Error while creating network from template: %s.";
        public static final String ERROR_WHILE_CREATING_REQUEST_BODY = "Error while creating request body.";
        public static final String ERROR_WHILE_CREATING_SECURITY_GROUPS_FROM_TEMPLATE_S = "Error while creating security groups from template: %s.";
        public static final String ERROR_WHILE_GETTING_INFO_ABOUT_GROUP_S_S = "Error while getting info about group %s: %s.";
        public static final String ERROR_WHILE_GETTING_TEMPLATES_S = "Error while getting info about templates: %s.";
        public static final String ERROR_WHILE_GETTING_USER_S_S = "Error while getting info about user %s: %s.";
        public static final String ERROR_WHILE_GETTING_USERS_S = "Error while getting info about users: %s.";
        public static final String ERROR_WHILE_INSTATIATING_INSTANCE_FROM_TEMPLATE_S = "Error while instatiating instance from template: %s.";
        public static final String UNABLE_TO_CLOSE_FILE_S = "Unable to close file %s.";
        public static final String UNABLE_TO_GET_TOKEN_FROM_JSON = "Unable to get token from json.";
    }

    public static class Info {
        public static final String TEMPLATE_POOL_LENGTH_S = "Template pool length: %s.";
        public static final String USER_POOL_LENGTH_S = "User pool length: %s.";
    }

    public static final class Warn {
        public static final String UNABLE_TO_GENERATE_SIGNATURE = "Unable to generate signature.";
    }
}
