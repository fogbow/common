package cloud.fogbow.common.models.policy;

import java.util.Set;

import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.models.FogbowOperation;
import cloud.fogbow.common.util.ClassFactory;

public class PermissionInstantiator<T extends FogbowOperation> {
    private ClassFactory classFactory;
    
    public PermissionInstantiator(ClassFactory classFactory) {
        this.classFactory = classFactory;
    }
    
    public Permission<T> getPermissionInstance(String type, String ... params) throws InvalidParameterException {
        return (Permission<T>) this.classFactory.createPluginInstance(type, params);
    }

    public Permission<T> getPermissionInstance(String type, String name, Set<String> operations) throws InvalidParameterException {
        Permission<T> instance = (Permission<T>) this.classFactory.createPluginInstance(type);

        // Permission constructors require a Set as argument.
        // Since it is difficult to implement a ClassFactory able 
        // to use constructors that have interfaces in their
        // signatures, here we create Permissions using
        // the default constructor and then set the parameters.
        instance.setName(name);
        instance.setOperationTypes(operations);
        
        return instance;
    }
}
