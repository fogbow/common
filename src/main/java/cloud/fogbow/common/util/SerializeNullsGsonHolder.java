package cloud.fogbow.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SerializeNullsGsonHolder {
    private static Gson gson;

    private SerializeNullsGsonHolder() {
    }

    public static Gson getInstance() {
        if (gson == null) {
            gson = new GsonBuilder().serializeNulls().create();
        }
        return gson;
    }
}
