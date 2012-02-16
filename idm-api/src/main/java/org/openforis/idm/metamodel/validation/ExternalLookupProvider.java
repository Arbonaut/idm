/**
 * 
 */
package org.openforis.idm.metamodel.validation;

/**
 * @author M. Togna
 *
 */
public interface ExternalLookupProvider {

	Object lookup(String name, String attribute, Object... keys);
	
}
