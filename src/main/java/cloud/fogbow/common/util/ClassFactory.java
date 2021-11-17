package cloud.fogbow.common.util;

import cloud.fogbow.common.exceptions.FatalErrorException;

public interface ClassFactory {
	Object createPluginInstance(String pluginClassName, String ... params) throws FatalErrorException;
	Object createPluginInstance(String pluginClassName, Object ... params) throws FatalErrorException;
}
