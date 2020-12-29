package cloud.fogbow.common.models;

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
