/**
 * 
 */
package org.openforis.idm.model;

import org.openforis.idm.metamodel.validation.ExternalLookupProvider;
import org.openforis.idm.model.expression.LookupFunctionTest;

/**
 * @author M. Togna
 * 
 */
public class TestExternalLookupProviderImpl implements ExternalLookupProvider {

	@Override
	public Object lookup(String name, String attribute, Object... keys) {
		return LookupFunctionTest.TEST_COORDINATE;
	}

}
