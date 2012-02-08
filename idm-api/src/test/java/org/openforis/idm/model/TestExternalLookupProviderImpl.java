/**
 * 
 */
package org.openforis.idm.model;

import org.openforis.idm.model.expression.LookupFunctionTest;
import org.openforis.idm.validation.ExternalLookupProvider;

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
