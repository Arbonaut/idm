package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.ApplicationOptions;

/**
 * @author G. Miceli
 */
public interface ApplicationOptionsBinder<T extends ApplicationOptions> {
	/**
	 * 
	 * @param body the text or well-formed XML contained in the body of the 
	 * IDML options element 
	 * @return the converted {@link ApplicationOptions} object
	 */
	T unmarshal(String body);

	/**
	 * 
	 * @param options a valid {@link ApplicationOptions} object  
	 * @return the text or well-formed XML to be placed inside the IDML 
	 * options element
	 */
	String marshal(T options);
	
	
	/**
	 * 
	 * @param optionsType
	 * @return true if the optionType specified is supported by this marshallar
	 */
	boolean isSupported(String optionsType);
}
