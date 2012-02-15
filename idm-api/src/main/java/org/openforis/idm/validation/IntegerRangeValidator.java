/**
 * 
 */
package org.openforis.idm.validation;

import org.openforis.idm.model.IntegerRange;
import org.openforis.idm.model.IntegerRangeAttribute;

/**
 * @author M. Togna
 * 
 */
public class IntegerRangeValidator implements Validator<IntegerRangeAttribute> {

	@Override
	public boolean validate(IntegerRangeAttribute node) {
		IntegerRange range = node.getValue();
		Integer from = range.getFrom();
		Integer to = range.getTo();
		return to >= from;
	}

}
