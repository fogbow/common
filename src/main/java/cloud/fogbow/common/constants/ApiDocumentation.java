package cloud.fogbow.common.constants;

public class ApiDocumentation {
    public static class ApiInfo {
        public static final String CONTACT_NAME = "Fogbow team";
        public static final String CONTACT_URL = "https://www.fogbow.cloud";
        public static final String CONTACT_EMAIL = "contact@fogbow.cloud";
    }

    public static class PublicKey {
        public static final String API = "Queries the public key of the service.";
        public static final String GET_OPERATION = "Returns the public key of the service.";
    }

    public static class Version {
        public static final String API = "Queries the version of the service's API.";
        public static final String GET_OPERATION = "Returns the version of the service's API, and the build number.";
    }

    public static class Token {
        public static final String SYSTEM_USER_TOKEN = "This is the token that identifies a user in the system. " +
                                                       "It is created via a call to the /tokens endpoint of the " +
                                                       "Fogbow Authentication Service (AS).";
    }

    public static class Model {
        public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2uLMdBAGXUCZIHDf1NSs" +
                "Qvh9r72noQEMHQXw+lbKYuxxVzoMKjfa0qXPDvWIQ4E5wJO/VskhBNRZQsWbHPqk" +
                "MFzKlonzu+7KNzyF7Dd0E0KBGfzNWTSeaPXvpUgG7uRULn206mmgOTRWeG+HXbzF" +
                "jtpOVif3F0w+yQsQ2nSFVPTXXdX7pEAbDIMdH0I+Nb3y1Yl5ZJsO+rZcIUK0td7k" +
                "xM+BnKyQTyLkWIocwiw6WXHLOrwEKYDzv35uSha8+iB68kXbehWJxD7mG//WdVzW" +
                "3Rf7gmkApzPbOkeMoKOZJOS7DNkeOl150WbilLURQ7gHH6EiyDqskIlyRYiW6FDF" +
                "+wIDAQAB";
    }
}
