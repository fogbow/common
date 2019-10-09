package cloud.fogbow.common.util.connectvity.cloud.cloudstack;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.junit.Assert;
import org.junit.Test;

import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.InstanceNotFoundException;
import cloud.fogbow.common.exceptions.UnauthenticatedUserException;
import cloud.fogbow.common.exceptions.UnauthorizedRequestException;
import cloud.fogbow.common.util.connectivity.cloud.cloudstack.CloudStackHttpToFogbowExceptionMapper;

public class CloudStackHttpToFogbowExceptionMapperTest {

	private static final String MESSAGE = "message";
	
    // test case: When calling the get method with a forbidden(403) 
	// HttpResponseException, it must verify if It returns an UnauthorizedRequestException.	
	@Test
	public void testGetWithFobbidenHttpResponseException() throws FogbowException {
		// set up
		HttpResponseException e = new HttpResponseException(HttpStatus.SC_FORBIDDEN, MESSAGE);
		
		// exercise
		FogbowException fogbowException = CloudStackHttpToFogbowExceptionMapper.get(e);
		
		// verify
		Assert.assertTrue(fogbowException instanceof UnauthorizedRequestException);
		Assert.assertEquals(e.getMessage(), fogbowException.getMessage());
	}
	
    // test case: When calling the get method with an unauthorized(401) 
	// HttpResponseException, it must verify if It returns an UnauthenticatedUserException.	
	@Test
	public void testGetWithUnauthorizedHttpResponseException() throws FogbowException {
		// set up
		HttpResponseException e = new HttpResponseException(HttpStatus.SC_UNAUTHORIZED, MESSAGE);
		
		// exercise
		FogbowException fogbowException = CloudStackHttpToFogbowExceptionMapper.get(e);
		
		// verify
		Assert.assertTrue(fogbowException instanceof UnauthenticatedUserException);
		Assert.assertEquals(e.getMessage(), fogbowException.getMessage());
	}
	
    // test case: When calling the get method with a not found(404) 
	// HttpResponseException, it must verify if It returns an InstanceNotFoundException.
	@Test
	public void testGetWithNotFoundHttpResponseException() throws FogbowException {
		// set up
		HttpResponseException e = new HttpResponseException(HttpStatus.SC_NOT_FOUND, MESSAGE);
		
		// exercise
		FogbowException fogbowException = CloudStackHttpToFogbowExceptionMapper.get(e);
		
		// verify
		Assert.assertTrue(fogbowException instanceof InstanceNotFoundException);
		Assert.assertEquals(e.getMessage(), fogbowException.getMessage());
	}
	
    // test case: When calling the get method with any unmapped 
	// HttpResponseException, it must verify if It returns an FogbowException.
	@Test
	public void testGetWithUnmappedHttpResponseException() throws FogbowException {
		// set up
		int anyOtherStatusCode = 0;
		HttpResponseException e = new HttpResponseException(anyOtherStatusCode, MESSAGE);
		
		// exercise
		FogbowException fogbowException = CloudStackHttpToFogbowExceptionMapper.get(e);
		
		// verify
		Assert.assertTrue(fogbowException instanceof FogbowException);
		Assert.assertEquals(e.getMessage(), fogbowException.getMessage());
	}		
	
}
