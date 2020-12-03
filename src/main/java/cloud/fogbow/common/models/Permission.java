package cloud.fogbow.common.models;

public interface Permission<T extends FogbowOperation> {

    boolean isAuthorized(T operation);
}
