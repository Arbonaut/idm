package org.openforis.idm.model.expression.internal;

import org.apache.commons.jxpath.ExpressionContext;
import org.openforis.idm.metamodel.validation.LookupProvider;
import org.openforis.idm.model.Node;

/**
 * 
 * Custom xpath functions allowed into IDM
 * 
 * @author M. Togna
 * 
 */
public class IDMFunctions {
	public static boolean blank(ExpressionContext context, Object object) {
		try {
		//	return node.isEmpty();
			return object == null;
		} catch ( MissingValueException e ) {
			return true;
		}
	}
	
	public static boolean blank(ExpressionContext context, Node<?> node) {
		try {
			return node.isEmpty();
		} catch ( MissingValueException e ) {
			return true;
		}
	}

	public static Object lookup(ExpressionContext context, String name, String attribute, String column, String value) {
		return internalLookup(context, name, attribute, column, value);
	}

	public static Object lookup(ExpressionContext context, String name, String attribute, String column1, String value1, String column2, String value2) {
		return internalLookup(context, name, attribute, column1, value1, column2, value2);
	}

	public static Object lookup(ExpressionContext context, String name, String attribute, String column1, String value1, String column2, String value2, String column3, String value3) {
		return internalLookup(context, name, attribute, column1, value1, column2, value2, column3, value3);
	}

	private static Object internalLookup(ExpressionContext context, String name, String attribute, String... keys) {
		LookupProvider lookupProvider = getLookupProvider(context);
		Object object = lookupProvider.lookup(name, attribute, (Object[]) keys);
		return object;
	}

	private static LookupProvider getLookupProvider(ExpressionContext context) {
		ModelJXPathContext jxPathContext = (ModelJXPathContext) context.getJXPathContext();
		LookupProvider lookupProvider = jxPathContext.getLookupProvider();
		return lookupProvider;
	}

}
