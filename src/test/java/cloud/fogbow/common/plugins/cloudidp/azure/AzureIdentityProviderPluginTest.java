package cloud.fogbow.common.plugins.cloudidp.azure;

import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.models.AzureUser;
import cloud.fogbow.common.util.AzureClientCacheManager;
import com.microsoft.azure.management.Azure;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AzureClientCacheManager.class})
public class AzureIdentityProviderPluginTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private AzureIdentityProviderPlugin azureIdentityProviderPlugin;
    private AzureUser azureUserDefault = new AzureUser("", "");

    @Before
    public void setUp() {
        this.azureIdentityProviderPlugin = Mockito.spy(new AzureIdentityProviderPlugin());
    }

    // test case:
    @Ignore
    @Test
    public void testGetCloudUserSuccessfully() {
        // set up
        // exercise
        // verify
    }

    // test case: When calling the authenticate method with mocked methods,
    // it must verify if it returns the same azureUser
    @Test
    public void testAuthenticateSuccessfully() throws UnauthenticatedUserException {
        // set up
        // TODO: Check how to mock this final class(Azure)
        Azure azure = null;
        PowerMockito.mockStatic(AzureClientCacheManager.class);
        PowerMockito.when(AzureClientCacheManager.getAzure(this.azureUserDefault))
                .thenReturn(azure);

        // exercise
        AzureUser azureUser = this.azureIdentityProviderPlugin.authenticate(this.azureUserDefault);

        // verify
        Assert.assertEquals(this.azureUserDefault, azureUser);
    }

    // test case: When calling the authenticate method with mocked methods,
    // it must verify if it throws an UnauthenticatedUserException
    @Test
    public void testAuthenticateFail() throws UnauthenticatedUserException {
        // set up
        PowerMockito.mockStatic(AzureClientCacheManager.class);
        PowerMockito.when(AzureClientCacheManager.getAzure(this.azureUserDefault))
                .thenThrow(new UnauthenticatedUserException());

        // verify
        this.expectedException.expect(UnauthenticatedUserException.class);

        // exercise
        this.azureIdentityProviderPlugin.authenticate(this.azureUserDefault);
    }

    // test case:
    @Ignore
    @Test
    public void testCheckCredentialsSuccessfully() {
        // set up
        // exercise
        // verify
    }

}
