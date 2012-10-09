package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.Configuration;

/**
 * @author G. Miceli
 */
public interface ConfigurationMarshaller<T extends Configuration> {
	
	/**
	 * 
	 * @param config a valid {@link Configuration} object  
	 * @return the text or well-formed XML to be placed inside the IDML 
	 * configuration element
	 */
	String marshal(T config);
}
