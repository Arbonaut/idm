/**
 * 
 */
package org.openforis.idm.metamodel.validation;

/**
 * @author M. Togna
 *
 */
public interface LookupProvider {

	Object lookup(String name, String attribute, Object... keys);
	
}
