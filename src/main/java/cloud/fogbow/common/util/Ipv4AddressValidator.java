package cloud.fogbow.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ipv4AddressValidator {
    private Pattern pattern;
    private Pattern cidrPattern;
    private Matcher matcher;

    private static final String OCTET = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
    private static final String IP_ADDRESS_PATTERN = String.format("^(%1$s\\.){3}%1$s$", OCTET);
    private static final String CIDR_PATTERN = String.format("^(%1$s\\.){3}%1$s(\\/([0-9]|[1-2][0-9]|3[0-2]))?$", OCTET);

    public Ipv4AddressValidator(){
        pattern = Pattern.compile(IP_ADDRESS_PATTERN);
        cidrPattern = Pattern.compile(CIDR_PATTERN);
    }

    public boolean validate(final String ip){
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public boolean validateCidr(final String ip){
        matcher = cidrPattern.matcher(ip);
        return matcher.matches();
    }
}


