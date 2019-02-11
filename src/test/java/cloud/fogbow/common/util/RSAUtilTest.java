package cloud.fogbow.common.util;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RSAUtilTest {

    @Test
    public void testGetKey() {
        // set up
        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4SIblb7cOt4UDd46CQs3P5nrltWS6fGDA2KtLiat4ZDvd6Nk2bq5XNYWxXQ2uJJE+JB3dIsU05KuTVvHEm0Gudt97rlWRPHrkOKUbAnh1gDMKk5Cwmy1H5tJrBhVi8+UxTmmvzGBhs2ZFVEKNhNPlDi+hIPI77hCKNoiDY63F0OvRlVuIzw0WcdzEvBBO13MNBbF8fJsHzsPjtIu85hFDDIAfqnR7RUa/N2nyOrLy29deW3rzH6B+cEO/17XVZSMWqFJjI59NIyMvily9r82QDa3q+wbFGHX2h0NivwTTuBEhnEu5qW9r6xaocsQm5pgZ8AAX+0WDMCTIcPUQTMIRwIDAQAB-----END PUBLIC KEY-----";
        String privateKey = "-----BEGIN PRIVATE KEY-----MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDhIhuVvtw63hQN3joJCzc/meuW1ZLp8YMDYq0uJq3hkO93o2TZurlc1hbFdDa4kkT4kHd0ixTTkq5NW8cSbQa5233uuVZE8euQ4pRsCeHWAMwqTkLCbLUfm0msGFWLz5TFOaa/MYGGzZkVUQo2E0+UOL6Eg8jvuEIo2iINjrcXQ69GVW4jPDRZx3MS8EE7Xcw0FsXx8mwfOw+O0i7zmEUMMgB+qdHtFRr83afI6svLb115bevMfoH5wQ7/XtdVlIxaoUmMjn00jIy+KXL2vzZANrer7BsUYdfaHQ2K/BNO4ESGcS7mpb2vrFqhyxCbmmBnwABf7RYMwJMhw9RBMwhHAgMBAAECggEBAM5LZ8WG190lZHD0wm9JFL4197gGSpIOWUP/5rsUfF7mZVhWo/ITdQlJsCINBJuKhKLUFnH3hAT/MrL2N2t99hzWsQXQVOnmWDoyNVjukT99CZh2kBwCatHkhNSqk7ECbMtwsYgfABabTPxIfzff6BrlCEb7T2PCjvVKNMwBi2pHRYgVsBSVfGIa9I/Q5xVDfTQJgEeW2v33l7o2Azpr7Qx1gTom/UUhk96LYj7JK8hGqse0348OGK3BHsdiUe0k6TuVO7Mdly2UJeifS4UKWjNRcMWTjwxxA4NOAECd3oNZR9AgjDbCqVEp8EZ/DJDIxnPdTzSWZKF6RJp4kHxSa+ECgYEA/zs64hPEpZxQxCMlhPH2FdmWmvcg3f3FA5a2vYi+7GagW+UvX11FlPTLwnH1DzC6/srthsR5oBBfHTok+mYeQQbGcHa3j+cSRElI9Ce1oTsYm8VJiO7zuntDCi5OluB2m5uBlMGY4Cp5182LMD+34otpR0rTpVxUShJpWN2JrVcCgYEA4c+sdP0KVFPKAv5ZBv02PC9fyAHyENyiU6nZl5XmPmpdBv41sZI5Xfw9QTVwXpRx2NeOBIuNZyuq1EgikbgCzm9ry0AuhRHZ4GMnulJZHkhVEffhnuwQYvK/G4r2t8dKdGfTGmoDAUCv6BwJDzrwiFptiJEYmXbfz0Y2RelktpECgYA3o+LnTCXvcI9O4taYD+N0yfWCHOPGYT8k1CNMWFY+1PSac/EK2huEKIWPAybIQ/hP+U8ktQpKsab6iSVsjPyFcoOqGsdTkK0zw8Iqr8lxt59avfyU5HqvqZVoz8WEMLTKoXDzS+0W2UuslvuD7jAZLAksaIyRHPqnFzJuFAWiiwKBgQC520lxnNfEopZRsnEqPH1IOiHWqCtJz59XxyPODVXNp5Pb7p3ZAqZvAe6U3lwXrIyr0ncUrJv8/8cCJEE3uMGukayiRLhFVHUEp/gJ9j9dmmFSgbjVKi0uk+fRzqXUi5ZviwkI45qHjGt8Wp7mRze0suEWl34sydfabUq8biYz0QKBgQCf2cuNtFapUuuzj1hOwLCMNg0xRl4rCbMwk8CYF9OPZT7cNfaXl7u+iGmE+NRQ19CkN171+jI6+NtKBv2KUzpLTxgpG10KMwEJG/ppJc4YI5477I18NFFwk7aD8906pgTmU7MFrQx+CdMcApzULBcF8UgufgAOMzmOYXIngFSUlQ==-----END PRIVATE KEY-----";

        String keysPath = HomeDir.getPath();
        String pubKeyPath = keysPath + "public.key";
        String privKeyPath = keysPath + "private.key";

        // exercise
        String actualPubKey = null;
        String actualPrivKey = null;
        try {
            actualPubKey = RSAUtil.getKey(pubKeyPath);
            actualPrivKey = RSAUtil.getKey(privKeyPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // verify
        assertEquals(publicKey, actualPubKey);
        assertEquals(privateKey, actualPrivKey);
    }
}
