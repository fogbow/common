package cloud.fogbow.common.util;

public class IntercomponentUtil {

    public static String getSender(String xmppJid, String prefix) {
        return xmppJid.replaceFirst(prefix, "");
    }
}
