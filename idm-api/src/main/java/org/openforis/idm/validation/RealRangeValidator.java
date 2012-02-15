/**
 * 
 */
package org.openforis.idm.validation;

import org.openforis.idm.model.RealRange;
import org.openforis.idm.model.RealRangeAttribute;

/**
 * @author M. Togna
 * 
 */
public class RealRangeValidator implements Validator<RealRangeAttribute> {

	@Override
	public boolean validate(RealRangeAttribute node) {
		RealRange range = node.getValue();
		Double from = range.getFrom();
		Double to = range.getTo();
		return to >= from;
	}

}
