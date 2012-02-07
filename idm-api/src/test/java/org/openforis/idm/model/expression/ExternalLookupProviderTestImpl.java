/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.validation.ExternalLookupProvider;

/**
 * @author M. Togna
 *
 */
public class ExternalLookupProviderTestImpl implements ExternalLookupProvider {
	
	@Override
	public Object lookup(String name, String attribute, Object... keys) {
		return LookupFunctionTest.TEST_COORDINATE;
	}

}
