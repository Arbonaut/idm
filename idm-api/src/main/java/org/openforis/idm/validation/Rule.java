/**
 * 
 */
package org.openforis.idm.validation;

import org.openforis.idm.model.Node;

/**
 * @author M. Togna
 *
 */
public interface Rule<R extends RuleResult ,N extends Node<?>> {

	R evaluate(N node);
	
}
