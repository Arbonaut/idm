package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.Configuration;

/**
 * @author G. Miceli
 */
public interface ConfigurationUnmarshaller<T extends Configuration> {

	/**
	 * 
	 * @param body the text or well-formed XML to be placed inside the IDML 
	 * configuration element 
	 * @return the converted {@link Configuration} object
	 */
	T unmarshal(String body);

}
