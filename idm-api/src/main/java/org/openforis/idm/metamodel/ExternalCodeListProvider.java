/**
 * 
 */
package org.openforis.idm.metamodel;

/**
 * @author M. Togna
 *
 */
public interface ExternalCodeListProvider {
	
	String getCode(CodeList list, String attribute, Object... keys);
	
}
