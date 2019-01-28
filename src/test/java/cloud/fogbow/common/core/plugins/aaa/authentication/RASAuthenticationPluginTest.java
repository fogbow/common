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
@PrepareForTest({RASAuthenticationHolder.class, PropertiesHolder.class})
@RunWith(PowerMockRunner.class)
public class RASAuthenticationPluginTest {

	private static final String DEFAULT_LOCAL_MEMBER_ID = "default_local_member_id";
	private RASAuthenticationPlugin rasAuthenticationPlugin;
	private PropertiesHolder propertiesHolder;
	private RASAuthenticationHolder rasAuthenticationHolder;

	@Before
	public void setUp() {		
		this.propertiesHolder = Mockito.mock(PropertiesHolder.class);
		PowerMockito.mockStatic(PropertiesHolder.class);
		BDDMockito.given(PropertiesHolder.getInstance()).willReturn(this.propertiesHolder);
		
		Mockito.when(this.propertiesHolder.getProperty(Mockito.eq(ConfigurationConstants.LOCAL_MEMBER_ID_KEY)))
					.thenReturn(DEFAULT_LOCAL_MEMBER_ID);
		
		this.rasAuthenticationHolder = Mockito.mock(RASAuthenticationHolder.class);
		PowerMockito.mockStatic(RASAuthenticationHolder.class);
		BDDMockito.given(RASAuthenticationHolder.getInstance()).willReturn(this.rasAuthenticationHolder);
		
		this.rasAuthenticationPlugin = Mockito.spy(new RASAuthenticationPluginWrapper());
	}
	
	// test case: Same local member and is authenticated
	@Test
	public void testIsAuthentic() throws FogbowRasException {
		// set up
		String requestingMember = DEFAULT_LOCAL_MEMBER_ID;
		FederationUser federationUser = new FederationUser(requestingMember, "", "", "");
			    	    
	    Mockito.when(this.rasAuthenticationHolder.verifySignature(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		
	    // exercise
		boolean isAuthenticated = this.rasAuthenticationPlugin.isAuthentic(requestingMember, federationUser);
		
		// verify
		Assert.assertTrue(isAuthenticated);
	}
	
	// test case: Same local member and is not authenticated
	@Test
	public void testIsNotAuthentic() throws FogbowRasException {
		// set up
		String requestingMember = DEFAULT_LOCAL_MEMBER_ID;
		FederationUser federationUser = new FederationUser(requestingMember, "", "", "");
			    	    
	    Mockito.when(this.rasAuthenticationHolder.verifySignature(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
		
	    // exercise
		boolean isAuthenticated = this.rasAuthenticationPlugin.isAuthentic(requestingMember, federationUser);
		
		// verify
		Assert.assertFalse(isAuthenticated);
	}	
	
	// test case: The requesting member is not equals to local member and is equals to federationUser 
	@Test
	public void testIsAuthenticRequestingMemberEqualsToToken() throws FogbowRasException {
		// set up
		String requestingMember = "requestingmember";
		FederationUser federationUser = new FederationUser(requestingMember, "", "", "");
			    	    
	    // exercise
		boolean isAuthenticated = this.rasAuthenticationPlugin.isAuthentic(requestingMember, federationUser);
		
		// verify
		Mockito.verify(this.rasAuthenticationHolder, Mockito.never()).verifySignature(Mockito.anyString(), Mockito.anyString());
		Mockito.verify(this.rasAuthenticationPlugin, Mockito.never()).getTokenMessage(Mockito.any(FederationUser.class));
		Assert.assertTrue(isAuthenticated);
	}	
	
	// test case: The requesting member is not equals to local member and is not equals to federationUser 
	@Test
	public void testIsAuthenticRequestingMemberNotEqualsToToken() throws FogbowRasException {
		// set up
		String requestingMember = "requestingmember";
		String requestingMemberOther = "other";
		FederationUser federationUser = new FederationUser(requestingMemberOther, "", "", "");
			    	    
	    // exercise
		boolean isAuthenticated = this.rasAuthenticationPlugin.isAuthentic(requestingMember, federationUser);
		
		// verify
		Mockito.verify(this.rasAuthenticationHolder, Mockito.never()).verifySignature(Mockito.anyString(), Mockito.anyString());
		Mockito.verify(this.rasAuthenticationPlugin, Mockito.never()).getTokenMessage(Mockito.any(FederationUser.class));
		Assert.assertFalse(isAuthenticated);
	}	
	
	public class RASAuthenticationPluginWrapper extends RASAuthenticationPlugin {

		@Override
		protected String getTokenMessage(FederationUser federationUser) {return null;}

		@Override
		protected String getSignature(FederationUser federationUser) {return null;}
		
	}
	
}
