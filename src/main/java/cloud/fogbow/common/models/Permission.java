package cloud.fogbow.common.models;

public interface Permission<T extends FogbowOperation> {
	// TODO documentation
    boolean isAuthorized(T operation);
}
