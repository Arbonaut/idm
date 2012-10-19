/**
 * 
 */
package org.openforis.idm.metamodel;

/**
 * @author M. Togna
 *
 */
public interface ExternalCodeListProvider {
	
	String getCode(String listName, String attribute, Object... keys);
	
}
