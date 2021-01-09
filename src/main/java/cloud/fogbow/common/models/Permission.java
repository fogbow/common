package cloud.fogbow.common.models;

import java.util.Set;

public interface Permission<T extends FogbowOperation> {
	/**
	 * Verifies whether or not given operation is authorized.
	 * 
	 * @param operation the operation object to verify.
	 * @return a boolean stating whether the operation is authorized or not. 
	 */
    boolean isAuthorized(T operation);
    
    /**
     * Returns the operation types used by the permission instance.
     * 
     * @return a Set containing the operation types.
     */
    Set getOperationsTypes();
    
    /**
     * Modifies the operation types used by the permission instance.
     * 
     * @param operations a Set containing the new operation types.
     */
    void setOperationTypes(Set operations);
    
    /**
     * Modifies the name of the permission instance.
     * 
     * @param name the new name of the permission.
     */
    void setName(String name);
}
