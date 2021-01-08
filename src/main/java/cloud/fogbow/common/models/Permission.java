package cloud.fogbow.common.models;

import java.util.Set;

public interface Permission<T extends FogbowOperation> {
	// TODO documentation
    boolean isAuthorized(T operation);
    
    // TODO documentation
    Set getOperationsTypes();
    
    // TODO documentation
    void setOperationTypes(Set operations);
    
    // TODO documentation
    void setName(String name);
}
