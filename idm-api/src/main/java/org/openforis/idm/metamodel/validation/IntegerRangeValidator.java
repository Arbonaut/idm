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
	public ValidationResultFlag evaluate(IntegerRangeAttribute node) {
		IntegerRange range = node.getValue();
		Integer from = range.getFrom();
		Integer to = range.getTo();
		boolean valid = to >= from;
		return ValidationResultFlag.valueOf(valid);
	}

}
