package cloud.fogbow.common.util;

import cloud.fogbow.common.models.FederationUser;

public class FederationUserUtil {
    public static String serialize(FederationUser federationUser) {
        return GsonHolder.getInstance().toJson(federationUser);
    }
    
    public static FederationUser deserialize(String federationUserString) {
        return GsonHolder.getInstance().fromJson(federationUserString, FederationUser.class);
    }
}