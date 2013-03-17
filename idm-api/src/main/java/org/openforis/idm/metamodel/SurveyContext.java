/**
 * 
 */
package org.openforis.idm.metamodel;

import org.openforis.idm.metamodel.validation.Validator;
import org.openforis.idm.model.expression.ExpressionFactory;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public interface SurveyContext {

	ExpressionFactory getExpressionFactory();

	Validator getValidator();

	ExternalCodeListProvider getExternalCodeListProvider();

	Survey createSurvey();
}
