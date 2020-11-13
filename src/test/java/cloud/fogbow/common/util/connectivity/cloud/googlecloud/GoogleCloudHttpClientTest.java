package cloud.fogbow.common.util.connectivity.cloud.googlecloud;

import cloud.fogbow.common.constants.GoogleCloudConstants;
import cloud.fogbow.common.constants.HttpConstants;
import cloud.fogbow.common.constants.HttpMethod;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.models.GoogleCloudUser;
import cloud.fogbow.common.util.connectivity.HttpRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class GoogleCloudHttpClientTest {
    private static final String TOKEN = "any-token";
    private static final String ANY_URL = "any-url";

    private GoogleCloudHttpClient client;
    private GoogleCloudUser user;

    @Before
    public void setUp() {
        this.client = new GoogleCloudHttpClient();
        this.user = createUser();
    }

    private GoogleCloudUser createUser() {
        GoogleCloudUser user = Mockito.mock(GoogleCloudUser.class);
        Mockito.when(user.getToken()).thenReturn(TOKEN);
        return user;
    }

    @Test
    public void testPrepareRequestGET() throws FogbowException {
        // set up
        HttpRequest genericRequest = new HttpRequest(
                HttpMethod.GET,
                ANY_URL,
                new HashMap<>(),
                new HashMap<>()
        );

        // exercise
        HttpRequest preparedRequest = this.client.prepareRequest(genericRequest, this.user);

        // verify
        Assert.assertEquals(HttpMethod.GET, preparedRequest.getMethod());
        Assert.assertEquals(ANY_URL, preparedRequest.getUrl());

        Map<String, String> headers = preparedRequest.getHeaders();
        Assert.assertTrue(headers.containsKey(GoogleCloudConstants.AUTHORIZATION_KEY));
        Assert.assertTrue(headers.containsKey(HttpConstants.ACCEPT_KEY));
        Assert.assertTrue(headers.containsKey(HttpConstants.CONTENT_TYPE_KEY));

        String expectedBearerToken = String.format(GoogleCloudConstants.BEARER_S, TOKEN);
        Assert.assertEquals(expectedBearerToken, headers.get(GoogleCloudConstants.AUTHORIZATION_KEY));

        String jsonContentType = HttpConstants.JSON_CONTENT_TYPE_KEY;
        Assert.assertEquals(jsonContentType, headers.get(HttpConstants.ACCEPT_KEY));
        Assert.assertEquals(jsonContentType, headers.get(HttpConstants.CONTENT_TYPE_KEY));
    }

    @Test
    public void testPrepareRequestPOST() throws FogbowException {
        // set up
        HttpRequest genericRequest = new HttpRequest(
                HttpMethod.POST,
                ANY_URL,
                new HashMap<>(),
                new HashMap<>()
        );

        // exercise
        HttpRequest preparedRequest = this.client.prepareRequest(genericRequest, this.user);

        // verify
        Assert.assertEquals(HttpMethod.POST, preparedRequest.getMethod());
        Assert.assertEquals(ANY_URL, preparedRequest.getUrl());

        Map<String, String> headers = preparedRequest.getHeaders();
        Assert.assertTrue(headers.containsKey(GoogleCloudConstants.AUTHORIZATION_KEY));
        Assert.assertTrue(headers.containsKey(HttpConstants.ACCEPT_KEY));
        Assert.assertTrue(headers.containsKey(HttpConstants.CONTENT_TYPE_KEY));

        String expectedBearerToken = String.format(GoogleCloudConstants.BEARER_S, TOKEN);
        Assert.assertEquals(expectedBearerToken, headers.get(GoogleCloudConstants.AUTHORIZATION_KEY));

        String jsonContentType = HttpConstants.JSON_CONTENT_TYPE_KEY;
        Assert.assertEquals(jsonContentType, headers.get(HttpConstants.ACCEPT_KEY));
        Assert.assertEquals(jsonContentType, headers.get(HttpConstants.CONTENT_TYPE_KEY));
    }
}
