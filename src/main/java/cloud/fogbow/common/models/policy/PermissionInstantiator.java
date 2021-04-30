package cloud.fogbow.common.models.policy;

import java.util.Set;

import cloud.fogbow.common.models.FogbowOperation;

public interface PermissionInstantiator<T extends FogbowOperation> {
    Permission<T> getPermissionInstance(String type, String ... params);
    Permission<T> getPermissionInstance(String type, String name, Set<String> operations);
}
