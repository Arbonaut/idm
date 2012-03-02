/**
 * 
 */
package org.openforis.idm.metamodel.validation;


import org.openforis.idm.model.IntegerRange;
import org.openforis.idm.model.IntegerRangeAttribute;

/**
 * @author M. Togna
 * 
 */
public class IntegerRangeValidator implements ValidationRule<IntegerRangeAttribute> {

	@Override
	public boolean evaluate(IntegerRangeAttribute node) {
		IntegerRange range = node.getValue();
		Integer from = range.getFrom();
		Integer to = range.getTo();
		return to >= from;
	}

}
