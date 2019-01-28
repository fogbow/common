package cloud.fogbow.common.core.plugins.aaa.authentication;

import cloud.fogbow.common.core.PropertiesHolder;
import cloud.fogbow.common.core.constants.ConfigurationConstants;
import cloud.fogbow.common.models.FederationUser;
import org.fogbowcloud.ras.core.plugins.aaa.RASAuthenticationHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@PowerMockIgnore({"javax.management.*"})
@PrepareForTest({RASAuthenticationHolder.class})
@RunWith(PowerMockRunner.class)
public class RASTimestampedAuthenticationPluginTest {

	private RASTimestampedAuthenticationPlugin rasTimestamped;
	private RASAuthenticationHolder rasAuthenticationHolder;
	
	@Before
	public void setUp() {
		this.rasAuthenticationHolder = Mockito.mock(RASAuthenticationHolder.class);
		PowerMockito.mockStatic(RASAuthenticationHolder.class);
		BDDMockito.given(RASAuthenticationHolder.getInstance()).willReturn(this.rasAuthenticationHolder);
		String localMemberId = PropertiesHolder.getInstance().getProperty(ConfigurationConstants.LOCAL_MEMBER_ID_KEY);
		this.rasTimestamped = new RASTimestampedAuthenticationPluginWraper(localMemberId);
	}

	// test case: Success case
	@Test
	public void testCheckValidity() {
		// set up
		long timestamp = System.currentTimeMillis();
		Mockito.when(this.rasAuthenticationHolder.checkValidity(Mockito.eq(timestamp))).thenReturn(true);
		
		// exercise and verify 
		Assert.assertTrue(this.rasTimestamped.checkValidity(timestamp));
	}
	
	private class RASTimestampedAuthenticationPluginWraper extends RASTimestampedAuthenticationPlugin {
		protected RASTimestampedAuthenticationPluginWraper(String providerId) {
			super(providerId);
		}

		@Override
		protected String getTokenMessage(FederationUser federationUser) {return null;}

		@Override
		protected String getSignature(FederationUser federationUser) {return null;}
		
	}
	
}
