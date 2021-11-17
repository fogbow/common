package cloud.fogbow.common.models.policy;

import cloud.fogbow.common.models.FogbowOperation;

public class Role<T extends FogbowOperation>{
    private String name;
    private String permissionName;
    
    public Role(String name, String permissionName) {
        this.name = name;
        this.permissionName = permissionName;
    }
    
    public String getName() {
        return name;
    }

    public String getPermission() {
    	return permissionName;
    }
}
