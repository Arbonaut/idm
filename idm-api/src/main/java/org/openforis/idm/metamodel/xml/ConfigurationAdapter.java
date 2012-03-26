package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.Configuration;
import org.w3c.dom.Element;

/**
 * @author G. Miceli
 */
public interface ConfigurationAdapter<T extends Configuration> {
	
	T unmarshal(Element elem);

	Element marshal(T config);
}
