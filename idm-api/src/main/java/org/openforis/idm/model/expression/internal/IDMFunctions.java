package org.openforis.idm.model.expression.internal;

import org.apache.commons.jxpath.ExpressionContext;
import org.openforis.idm.validation.ExternalLookupProvider;

public class IDMFunctions {

	public static Object lookup(ExpressionContext context, String name, String attribute, Object key) {
		ExternalLookupProvider externalLookupProvider = getExternalLookupProvider(context);
		Object object = externalLookupProvider.lookup(name, attribute, key);
		return object;
	}

	public static Object lookup(ExpressionContext context, String name, String attribute, Object key, Object key2) {
		ExternalLookupProvider externalLookupProvider = getExternalLookupProvider(context);
		Object object = externalLookupProvider.lookup(name, attribute, key, key2);
		return object;
	}

	private static ExternalLookupProvider getExternalLookupProvider(ExpressionContext context) {
		ModelJXPathContext jxPathContext = (ModelJXPathContext) context.getJXPathContext();
		ExternalLookupProvider externalLookupProvider = jxPathContext.getExternalLookupProvider();
		return externalLookupProvider;
	}

}
