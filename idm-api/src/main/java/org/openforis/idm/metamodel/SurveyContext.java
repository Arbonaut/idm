/**
 * 
 */
package org.openforis.idm.metamodel;

import org.openforis.idm.metamodel.validation.Validator;
import org.openforis.idm.model.expression.ExpressionFactory;

/**
 * @author M. Togna
 * 
 */
public interface SurveyContext {

	ExpressionFactory getExpressionFactory();

	Validator getValidator();
	
}
