/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import org.junit.BeforeClass;
import org.openforis.idm.AbstractTest;
import org.openforis.idm.model.Attribute;

/**
 * @author M. Togna
 *
 */
public abstract class ValidationTest  extends AbstractTest{

	protected static Validator validator;
	
	@BeforeClass
	public static void init(){
		validator = new Validator();
	}
	
	protected ValidationResults validate(Attribute<?, ?> attribute){
//		NodeState nodeState = new NodeState(node);
//		return validator.validate(nodeState);
		ValidationResults validationResults = attribute.validateValue();
		return validationResults;
	}
	
}
