package cloud.fogbow.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ipv4AddressValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String IP_ADDRESS_PATTERN = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$";

    public Ipv4AddressValidator(){
        pattern = Pattern.compile(IP_ADDRESS_PATTERN);
    }

    public boolean validate(final String ip){
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }


}
