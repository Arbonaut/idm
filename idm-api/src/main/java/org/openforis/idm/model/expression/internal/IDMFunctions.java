package org.openforis.idm.model.expression.internal;

import org.apache.commons.jxpath.ExpressionContext;
import org.openforis.idm.metamodel.validation.LookupProvider;

public class IDMFunctions {

	public static Object lookup(ExpressionContext context, String name, String attribute, Object key) {
		LookupProvider lookupProvider = getLookupProvider(context);
		Object object = lookupProvider.lookup(name, attribute, key);
		return object;
	}

	public static Object lookup(ExpressionContext context, String name, String attribute, Object key, Object key2) {
		LookupProvider lookupProvider = getLookupProvider(context);
		Object object = lookupProvider.lookup(name, attribute, key, key2);
		return object;
	}

	private static LookupProvider getLookupProvider(ExpressionContext context) {
		ModelJXPathContext jxPathContext = (ModelJXPathContext) context.getJXPathContext();
		LookupProvider lookupProvider = jxPathContext.getLookupProvider();
		return lookupProvider;
	}

}
