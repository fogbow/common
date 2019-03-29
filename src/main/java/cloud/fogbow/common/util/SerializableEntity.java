package cloud.fogbow.common.util;

public class SerializableEntity<T> {

    private String className;
    private String payload;

    public SerializableEntity(T instanceToSerialize) {
        this.className = instanceToSerialize.getClass().getName();
        this.payload = GsonHolder.getInstance().toJson(instanceToSerialize);
    }

    public T getSerializedEntity() throws ClassNotFoundException {
        return (T) GsonHolder.getInstance().fromJson(this.payload, Class.forName(this.className));
    }
}
