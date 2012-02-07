/**
 * 
 */
package org.openforis.idm.model.expression;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jxpath.ClassFunctions;
import org.apache.commons.jxpath.FunctionLibrary;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathContextFactory;
import org.apache.commons.jxpath.JXPathIntrospector;
import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;
import org.apache.commons.jxpath.ri.model.NodePointerFactory;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.expression.internal.IDMFunctions;
import org.openforis.idm.model.expression.internal.ModelJXPathCompiledExpression;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;
import org.openforis.idm.model.expression.internal.ModelNodePointerFactory;
import org.openforis.idm.model.expression.internal.ModelPropertyHandler;
import org.openforis.idm.model.expression.internal.RecordPropertyHandler;
import org.openforis.idm.validation.ExternalLookupProvider;

/**
 * @author M. Togna
 * 
 */
public class ExpressionFactory {

	private ModelJXPathContext jxPathContext;
	private ExternalLookupProvider externalLookupProvider;
	private Map<String, AbstractExpression> cachedExpressions;

	public ExpressionFactory() {
		System.setProperty(JXPathContextFactory.FACTORY_NAME_PROPERTY, "org.openforis.idm.model.expression.internal.ModelJXPathContextFactory");

		NodePointerFactory nodePointerFactory = new ModelNodePointerFactory();
		JXPathContextReferenceImpl.addNodePointerFactory(nodePointerFactory);

		JXPathIntrospector.registerDynamicClass(Node.class, ModelPropertyHandler.class);
		JXPathIntrospector.registerDynamicClass(Record.class, RecordPropertyHandler.class);

		jxPathContext = (ModelJXPathContext) JXPathContext.newContext(null);

		cachedExpressions = new HashMap<String, AbstractExpression>();
	}

	public CheckExpression createCheckExpression(String expression) {
		AbstractExpression expr = cachedExpressions.get(expression);
		if (expr == null) {
			ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
			expr = new CheckExpression(compiledExpression, jxPathContext);
			cachedExpressions.put(expression, expr);
		}
		return (CheckExpression) expr;
	}

	public ConditionalExpression createConditionalExpression(String expression) {
		AbstractExpression expr = cachedExpressions.get(expression);
		if (expr == null) {
			ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
			expr = new ConditionalExpression(compiledExpression, jxPathContext);
			cachedExpressions.put(expression, expr);
		}
		return (ConditionalExpression) expr;
	}

	public DefaultValueExpression createDefaultValueExpression(String expression) {
		AbstractExpression expr = cachedExpressions.get(expression);
		if (expr == null) {
			ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
			expr = new DefaultValueExpression(compiledExpression, jxPathContext);
			cachedExpressions.put(expression, expr);
		}
		return (DefaultValueExpression) expr;
	}

	public ModelPathExpression createModelPathExpression(String expression) {
		AbstractExpression expr = cachedExpressions.get(expression);
		if (expr == null) {
			ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
			expr = new ModelPathExpression(compiledExpression, jxPathContext);
			cachedExpressions.put(expression, expr);
		}
		return (ModelPathExpression) expr;
	}

	public RelevanceExpression createRelevanceExpression(String expression) {
		AbstractExpression expr = cachedExpressions.get(expression);
		if (expr == null) {
			ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
			expr = new RelevanceExpression(compiledExpression, jxPathContext);
			cachedExpressions.put(expression, expr);
		}
		return (RelevanceExpression) expr;
	}

	public RequiredExpression createRequiredExpression(String expression) {
		AbstractExpression expr = cachedExpressions.get(expression);
		if (expr == null) {
			ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
			expr = new RequiredExpression(compiledExpression, jxPathContext);
			cachedExpressions.put(expression, expr);
		}
		return (RequiredExpression) expr;
	}

	public ExternalLookupProvider getExternalLookupProvider() {
		return externalLookupProvider;
	}

	public void setExternalLookupProvider(ExternalLookupProvider externalLookupProvider) {
		this.externalLookupProvider = externalLookupProvider;
		jxPathContext.setExternalLookupProvider(externalLookupProvider);

		FunctionLibrary functionLibrary = new FunctionLibrary();
		functionLibrary.addFunctions(new ClassFunctions(IDMFunctions.class, "idm"));
		jxPathContext.setFunctions(functionLibrary);
	}

	private static ModelJXPathCompiledExpression compileExpression(String expression) {
		String normalizedExpression = getNormalizedExpression(expression);
		ModelJXPathCompiledExpression compiled = (ModelJXPathCompiledExpression) JXPathContext.compile(normalizedExpression);
		return compiled;
	}

	protected static String getNormalizedExpression(String expression) {
		return expression.replaceAll("\\bparent\\(\\)", "__parent");
	}

}
