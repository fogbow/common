package cloud.fogbow.common.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cloud.fogbow.common.exceptions.FatalErrorException;
import cloud.fogbow.common.stubs.StubClassForFactory;

public class ClassFactoryTest {

	private static final String ONE_PARAMETER = "parameter1";
	private static final String INVALID_CLASS_PATH = "invalid.class.path.InvalidClass";
	private static final String TWO_PARAMETER = "parameter2";
	private static final String STUB_CLASS_PATH = "cloud.fogbow.common.stubs.StubClassForFactory";
	private static final String STUB_CLASS_WITH_PRIVATE_CONSTRUCTOR_PATH = "cloud.fogbow.common.stubs.StubClassForFactoryWithPrivateConstructor";

	private ClassFactory classFactory;

	@Before
	public void setUp() {
		this.classFactory = new ClassFactory();
	}

	// test case: When invoking the createPluginInstance method without parameters
	// and with a valid class path, it must create the object from the constructor
	// without parameters of the specified class.
	@Test
	public void testCreatePluginInstanceNoParametersSuccessful() {
		// set up
		String className = STUB_CLASS_PATH;

		// exercise
		StubClassForFactory object = (StubClassForFactory) this.classFactory.createPluginInstance(className);

		// verify
		Assert.assertNotNull(object);
	}

	// test case: When invoking the createPluginInstance method with a parameter and
	// a valid class path, it must create the object from the constructor with only
	// the parameter of the specified class.
	@Test
	public void testCreatePluginInstanceWithOneParameterSuccessful() {
		// set up
		String className = STUB_CLASS_PATH;
		String parameter = ONE_PARAMETER;

		// exercise
		StubClassForFactory object = (StubClassForFactory) this.classFactory.createPluginInstance(className, parameter);

		// verify
		Assert.assertNotNull(object);
	}

	// test case: When invoking the createPluginInstance method with two parameters
	// and a valid class path, it must create the object from the constructor with
	// the parameters of the specified class.
	@Test
	public void testCreatePluginInstanceWithTwoParametersSuccessful() {
		// set up
		String className = STUB_CLASS_PATH;
		String parameter1 = ONE_PARAMETER;
		String parameter2 = TWO_PARAMETER;

		// exercise
		StubClassForFactory object = (StubClassForFactory) this.classFactory.createPluginInstance(className, parameter1,
				parameter2);

		// verify
		Assert.assertNotNull(object);
	}

	// test case: When invoking the createPluginInstance method without parameters
	// and without a valid class path, it must throw a FatalErrorException.
	@Test(expected = FatalErrorException.class) // veriry
	public void testCreatePluginInstanceNoParametersAndInvalidClassPath() {
		// set up
		String invalidClassPath = INVALID_CLASS_PATH;

		// exercise
		this.classFactory.createPluginInstance(invalidClassPath);
	}

	// test case: When invoking the createPluginInstance method with a parameter,
	// but without a valid class path, it must throw a FatalErrorException.
	@Test(expected = FatalErrorException.class) // veriry
	public void testCreatePluginInstanceWithOneParameterAndInvalidClassPath() {
		// set up
		String invalidClassPath = INVALID_CLASS_PATH;
		String parameter = ONE_PARAMETER;

		// exercise
		this.classFactory.createPluginInstance(invalidClassPath, parameter);
	}

	// test case: When invoking the createPluginInstance method with two parameters,
	// but without a valid class path, it must throw a FatalErrorException.
	@Test(expected = FatalErrorException.class) // veriry
	public void testCreatePluginInstanceWithTwoParametersAndInvalidClassPath() {
		// set up
		String invalidClassPath = INVALID_CLASS_PATH;
		String parameter1 = ONE_PARAMETER;
		String parameter2 = TWO_PARAMETER;

		// exercise
		this.classFactory.createPluginInstance(invalidClassPath, parameter1, parameter2);
	}

	// test case: When invoking the createPluginInstance method without parameters
	// and a class path without constructor or just a private constructor, it must
	// throw a FatalErrorException.
	@Test(expected = FatalErrorException.class) // verify
	public void testCreatePluginInstanceNoParametersAndPrivateConstructor() {
		// set up
		String className = STUB_CLASS_WITH_PRIVATE_CONSTRUCTOR_PATH;

		// exercise
		this.classFactory.createPluginInstance(className);
	}

	// test case: When invoking the createPluginInstance method with a parameter,
	// but a class path without constructor or just a private constructor, it must
	// throw a FatalErrorException.
	@Test(expected = FatalErrorException.class) // verify
	public void testCreatePluginInstanceWithOneParameterAndPrivateConstructor() {
		// set up
		String className = STUB_CLASS_WITH_PRIVATE_CONSTRUCTOR_PATH;
		String parameter = ONE_PARAMETER;

		// exercise
		this.classFactory.createPluginInstance(className, parameter);
	}

	// test case: When invoking the createPluginInstance method with two parameters,
	// but a class path without constructor or just a private constructor, it must
	// throw a FatalErrorException.
	@Test(expected = FatalErrorException.class) // verify
	public void testCreatePluginInstanceWithTwoParametersAndPrivateConstructor() {
		// set up
		String className = STUB_CLASS_WITH_PRIVATE_CONSTRUCTOR_PATH;
		String parameter1 = ONE_PARAMETER;
		String parameter2 = TWO_PARAMETER;

		// exercise
		this.classFactory.createPluginInstance(className, parameter1, parameter2);
	}

}