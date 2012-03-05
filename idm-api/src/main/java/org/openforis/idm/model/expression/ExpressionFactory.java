/**
 * 
 */
package org.openforis.idm.model.expression;

import org.apache.commons.jxpath.ClassFunctions;
import org.apache.commons.jxpath.FunctionLibrary;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathContextFactory;
import org.apache.commons.jxpath.JXPathIntrospector;
import org.apache.commons.jxpath.JXPathInvalidSyntaxException;
import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;
import org.apache.commons.jxpath.ri.model.NodePointerFactory;
import org.openforis.idm.metamodel.validation.LookupProvider;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.expression.internal.IDMFunctions;
import org.openforis.idm.model.expression.internal.ModelJXPathCompiledExpression;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;
import org.openforis.idm.model.expression.internal.ModelNodePointerFactory;
import org.openforis.idm.model.expression.internal.NodePropertyHandler;
import org.openforis.idm.model.expression.internal.RecordPropertyHandler;

/**
 * @author M. Togna
 * 
 */
public class ExpressionFactory {

	private ModelJXPathContext jxPathContext;
	private LookupProvider lookupProvider;

	public ExpressionFactory() {
		System.setProperty(JXPathContextFactory.FACTORY_NAME_PROPERTY, "org.openforis.idm.model.expression.internal.ModelJXPathContextFactory");

		NodePointerFactory nodePointerFactory = new ModelNodePointerFactory();
		JXPathContextReferenceImpl.addNodePointerFactory(nodePointerFactory);

		JXPathIntrospector.registerDynamicClass(Node.class, NodePropertyHandler.class);
		JXPathIntrospector.registerDynamicClass(Record.class, RecordPropertyHandler.class);

		jxPathContext = (ModelJXPathContext) JXPathContext.newContext(null);
	}

	public CheckExpression createCheckExpression(String expression) throws InvalidExpressionException {
		ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
		CheckExpression expr = new CheckExpression(compiledExpression, jxPathContext);
		return expr;
	}

	public CheckConditionExpression createCheckConditionExpression(String expression) throws InvalidExpressionException {
		ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
		CheckConditionExpression expr = new CheckConditionExpression(compiledExpression, jxPathContext);
		return expr;
	}

	public DefaultValueExpression createDefaultValueExpression(String expression) throws InvalidExpressionException {
		ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
		DefaultValueExpression expr = new DefaultValueExpression(compiledExpression, jxPathContext);
		return expr;
	}

	public DefaultConditionExpression createDefaultConditionExpression(String expression) throws InvalidExpressionException {
		ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
		DefaultConditionExpression expr = new DefaultConditionExpression(compiledExpression, jxPathContext);
		return expr;
	}

	public ModelPathExpression createModelPathExpression(String expression) throws InvalidExpressionException {
		ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
		ModelPathExpression expr = new ModelPathExpression(compiledExpression, jxPathContext);
		return expr;
	}

	public AbsoluteModelPathExpression createAbsoluteModelPathExpression(String expression) throws InvalidExpressionException {
		if ( !expression.startsWith("/") ) {
			throw new InvalidExpressionException("Absolute paths must start with '/'");
		}
		int pos = expression.indexOf('/', 1);
		if ( pos < 0 ) {
			String root = expression.substring(1);
			return new AbsoluteModelPathExpression(root);
		} else {
			String root = expression.substring(1, pos);
			expression = expression.substring(pos+1);
			ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
			return new AbsoluteModelPathExpression(root, compiledExpression, jxPathContext);		
		}
	}

	public RelevanceExpression createRelevanceExpression(String expression) throws InvalidExpressionException {
		ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
		RelevanceExpression expr = new RelevanceExpression(compiledExpression, jxPathContext);
		return expr;
	}

	public RequiredExpression createRequiredExpression(String expression) throws InvalidExpressionException {
		ModelJXPathCompiledExpression compiledExpression = compileExpression(expression);
		RequiredExpression expr = new RequiredExpression(compiledExpression, jxPathContext);
		return expr;
	}

	public LookupProvider getLookupProvider() {
		return lookupProvider;
	}

	public void setLookupProvider(LookupProvider lookupProvider) {
		this.lookupProvider = lookupProvider;
		jxPathContext.setLookupProvider(lookupProvider);

		FunctionLibrary functionLibrary = new FunctionLibrary();
		functionLibrary.addFunctions(new ClassFunctions(IDMFunctions.class, "idm"));
		jxPathContext.setFunctions(functionLibrary);
	}

	private static ModelJXPathCompiledExpression compileExpression(String expression) throws InvalidExpressionException {
		try {
			String normalizedExpression = getNormalizedExpression(expression);
			ModelJXPathCompiledExpression compiled = (ModelJXPathCompiledExpression) JXPathContext.compile(normalizedExpression);
			return compiled;
		} catch(JXPathInvalidSyntaxException e){
			throw new InvalidExpressionException(e.getMessage());
		}
	}

	protected static String getNormalizedExpression(String expression) {
		return expression.replaceAll("\\bparent\\(\\)", "__parent");
	}

}
