package cloud.fogbow.common.models;

public class Role<T extends FogbowOperation>{
    private String name;
    private Permission<T> permission;
    
    public Role(String name, Permission<T> permission) {
        this.name = name;
        this.permission = permission;
    }
    
    public String getName() {
        return name;
    }

    public boolean canPerformOperation(T operation) {
        return permission.isAuthorized(operation);
    }
}
