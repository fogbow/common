package cloud.fogbow.common.constants;

public class Messages {

    public static class Exception {
        public static final String AUTHENTICATION_ERROR = "Authentication error.";
        public static final String AUTHORIZATION_ERROR = "Authorization error.";
        public static final String CLASS_SHOULD_BE_CLONEABLE = "%s should be cloneable";
        public static final String CONFIGURATION_ERROR = "There's an error in configuration files.";
        public static final String FATAL_ERROR = "Fatal error.";
        public static final String FOGBOW = "Fogbow exception.";
        public static final String GENERIC_EXCEPTION = "Operation returned error: %s";
        public static final String INSTANCE_NOT_FOUND = "Instance not found.";
        public static final String INVALID_CREDENTIALS = "Invalid credentials.";
        public static final String INVALID_KEY = "Invalid char \"%c\" for random key: \"%s\" at index %d";
        public static final String INVALID_PARAMETER = "Invalid parameter.";
        public static final String INVALID_TOKEN = "Invalid token value.";
        public static final String NO_AVAILABLE_RESOURCES = "No available resources.";
        public static final String NULL_BODY_OR_HEADER = "Neither body nor headers can be null.";
        public static final String QUOTA_EXCEEDED = "Quota exceeded.";
        public static final String REMOTE_COMMUNICATION = "Error while sending message to remote RAS.";
        public static final String UNAVAILABLE_PROVIDER = "Provider is not available.";
        public static final String UNEXPECTED = "Unexpected error.";
        public static final String WRONG_URI_SYNTAX = "Wrong syntax for endpoint %s.";
    }

    public static class Fatal {
        public static final String PROPERTY_FILE_NOT_FOUND = "Property file %s not found.";
        public static final String UNABLE_TO_FIND_CLASS = "Unable to find class %s.";
        public static final String UNABLE_TO_READ_COMPOSED_AUTHORIZATION_PLUGIN_CONF_FILE =
                "Unable to read ComposedAuthorizationPlugin configuration file %s.";
    }

    public static final class Error {
        public static final String ERROR_MESSAGE = "Error message is: %s.";
        public static final String ERROR_WHILE_CONVERTING_INSTANCE_ID = "Error while converting instanceid %s to integer.";
        public static final String ERROR_WHILE_CREATING_CLIENT = "Error while creating client.";
        public static final String ERROR_WHILE_CREATING_IMAGE = "Error while creating a image from template: %s.";
        public static final String ERROR_WHILE_CREATING_NETWORK = "Error while creating a network from template: %s.";
        public static final String ERROR_WHILE_CREATING_REQUEST_BODY = "Error while creating request body.";
        public static final String ERROR_WHILE_CREATING_RESPONSE_BODY = "Error while creating response body.";
        public static final String ERROR_WHILE_CREATING_SECURITY_GROUPS = "Error while creating a security groups from template: %s.";
        public static final String ERROR_WHILE_GETTING_GROUP = "Error while getting info about group %s: %s.";
        public static final String ERROR_WHILE_GETTING_TEMPLATES = "Error while getting info about templates: %s.";
        public static final String ERROR_WHILE_GETTING_USER = "Error while getting info about user %s: %s.";
        public static final String ERROR_WHILE_GETTING_USERS = "Error while getting info about users: %s.";
        public static final String ERROR_WHILE_INSTANTIATING_FROM_TEMPLATE = "Error while instatiating an instance from template: %s.";
        public static final String UNABLE_TO_CLOSE_FILE = "Unable to close file %s.";
    }

    public static class Info {
        public static final String TEMPLATE_POOL_LENGTH = "Template pool length: %s.";
        public static final String USER_POOL_LENGTH = "User pool length: %s.";
    }

    public static final class Warn {
        public static final String UNABLE_TO_GENERATE_SIGNATURE = "Unable to generate signature.";
    }
}
