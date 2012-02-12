/**
 * 
 */
package org.openforis.idm.validation;

import org.openforis.idm.model.Node;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public interface Validator<N extends Node<?>> {

	boolean validate(N node);
	
}
