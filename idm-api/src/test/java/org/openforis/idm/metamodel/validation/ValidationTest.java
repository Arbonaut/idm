/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import org.junit.BeforeClass;
import org.openforis.idm.AbstractTest;
import org.openforis.idm.model.Node;

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
	
	protected ValidationResults validate(Node<?> node){
		return validator.validate(node);
	}
	
}
