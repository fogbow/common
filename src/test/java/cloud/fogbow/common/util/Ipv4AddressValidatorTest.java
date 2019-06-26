package cloud.fogbow.common.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Ipv4AddressValidatorTest {
    private Ipv4AddressValidator validator;

    @Before
    public void setup() {
        this.validator = new Ipv4AddressValidator();
    }

    @Test
    public void testValidIp() {
        // verify
        boolean isValid = this.validator.validate("10.30.0.225");
        assertTrue(isValid);
    }

    @Test
    public void testInvalidIp() {
        // verify
        checkInvalidIp("10.2.3.5000");
        checkInvalidIp("10.2.3.50.4");
        checkInvalidIp("random-text");
        checkInvalidIp("10.2.3.50.4");
    }

    @Test
    public void testValidCidr() {
        // verify
        boolean isValid = this.validator.validateCidr("1.2.3.4/30");
        assertTrue(isValid);
    }

    @Test
    public void testInvalidCidr() {
        checkInvalidCidr("1.2.3.4/40");
        checkInvalidCidr("1.2.3.4.5/30");
        checkInvalidCidr("1.2.3/30");
    }

    private void checkInvalidIp(String ip) {
        boolean isValid = this.validator.validate(ip);
        assertFalse(isValid);
    }

    private void checkInvalidCidr(String cidr) {
        boolean isValid = this.validator.validateCidr(cidr);
        assertFalse(isValid);
    }
}