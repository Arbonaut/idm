/**
 * 
 */
package org.openforis.idm.model;

import org.openforis.idm.metamodel.validation.Validator;
import org.openforis.idm.model.expression.ExpressionFactory;

/**
 * @author M. Togna
 * 
 */
public interface RecordContext {

	ExpressionFactory getExpressionFactory();

	Validator getValidator();
	
}
