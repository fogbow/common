package cloud.fogbow.common.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cloud.fogbow.common.exceptions.FatalErrorException;
import cloud.fogbow.common.stubs.StubClassForFactory;

public class ClassFactoryTest {

	private static final String INVALID_CLASS_PATH = "invalid.class.path.InvalidClass";
	private static final String ONE_PARAMETER = "parameter1";
	private static final String TWO_PARAMETER = "parameter2";
	private static final String STUB_CLASSNAME = "cloud.fogbow.common.stubs.StubClassForFactory";

	private ClassFactory classFactory;

	@Before
	public void setUp() {
		this.classFactory = new ClassFactory();
	}

	// test case: When invoking the createPluginInstance method without parameters
	// and with a valid class path, it must create the object from the constructor
	// without parameters of the specified class.
	@Test
	public void testCreatePluginInstanceNoParameters() {
		// exercise
		StubClassForFactory object = (StubClassForFactory) this.classFactory.createPluginInstance(STUB_CLASSNAME);

		// verify
		Assert.assertNotNull(object);
	}

	// test case: When invoking the createPluginInstance method with a parameter and
	// a valid class path, it must create the object from the constructor with only
	// the parameter of the specified class.
	@Test
	public void testCreatePluginInstanceWithOneParameter() {
		// exercise
		StubClassForFactory object = (StubClassForFactory) this.classFactory.createPluginInstance(STUB_CLASSNAME,
				ONE_PARAMETER);

		// verify
		Assert.assertNotNull(object);
	}

	// test case: When invoking the createPluginInstance method with two parameters
	// and a valid class path, it must create the object from the constructor with
	// the parameters of the specified class.
	@Test
	public void testCreatePluginInstanceWithTwoParameters() {
		// exercise
		StubClassForFactory object = (StubClassForFactory) this.classFactory.createPluginInstance(STUB_CLASSNAME,
				ONE_PARAMETER, TWO_PARAMETER);

		// verify
		Assert.assertNotNull(object);
	}

	// test case: When invoking the createPluginInstance method without parameters
	// and without a valid class path, it must throw a FatalErrorException.
	@Test(expected = FatalErrorException.class) // verify
	public void testCreatePluginInstanceNoParametersAndInvalidClassPath() {
		// exercise
		this.classFactory.createPluginInstance(INVALID_CLASS_PATH);
	}

	// test case: When invoking the createPluginInstance method with a parameter,
	// but without a valid class path, it must throw a FatalErrorException.
	@Test(expected = FatalErrorException.class) // verify
	public void testCreatePluginInstanceWithOneParameterAndInvalidClassPath() {
		// exercise
		this.classFactory.createPluginInstance(INVALID_CLASS_PATH, ONE_PARAMETER);
	}

	// test case: When invoking the createPluginInstance method with two parameters,
	// but without a valid class path, it must throw a FatalErrorException.
	@Test(expected = FatalErrorException.class) // verify
	public void testCreatePluginInstanceWithTwoParametersAndInvalidClassPath() {
		// exercise
		this.classFactory.createPluginInstance(INVALID_CLASS_PATH, ONE_PARAMETER, TWO_PARAMETER);
	}

	// test case: When invoking the createPluginInstance method without parameters
	// and a null class name, it must throw a FatalErrorException.
	@Test(expected = FatalErrorException.class) // verify
	public void testCreatePluginInstanceWithNullClassNameAndNoParameters() {
		// exercise
		this.classFactory.createPluginInstance(null);
	}
		
	// test case: When invoking the createPluginInstance method with one parameter
	// and a null class name, it must throw a FatalErrorException.
	@Test(expected = FatalErrorException.class) // verify
	public void testCreatePluginInstanceWithNullClassNameAndOneParameter() {
		// exercise
		this.classFactory.createPluginInstance(null, ONE_PARAMETER);
	}
		
	// test case: When invoking the createPluginInstance method with two parameters
	// and a null class name, it must throw a FatalErrorException.
	@Test(expected = FatalErrorException.class) // verify
	public void testCreatePluginInstanceWithNullClassNameAndTwoParameters() {
		// exercise
		this.classFactory.createPluginInstance(null, ONE_PARAMETER, TWO_PARAMETER);
	}

}