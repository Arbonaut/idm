/**
 * 
 */
package org.openforis.idm.model.expression;

import org.apache.commons.jxpath.ClassFunctions;
import org.apache.commons.jxpath.FunctionLibrary;
import org.apache.commons.jxpath.JXPathIntrospector;
import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;
import org.apache.commons.jxpath.ri.model.NodePointerFactory;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.expression.internal.IDMFunctions;
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

	public ExpressionFactory() {
		NodePointerFactory nodePointerFactory = new ModelNodePointerFactory();
		JXPathContextReferenceImpl.addNodePointerFactory(nodePointerFactory);

		JXPathIntrospector.registerDynamicClass(Node.class, ModelPropertyHandler.class);
		JXPathIntrospector.registerDynamicClass(Record.class, RecordPropertyHandler.class);

		jxPathContext = ModelJXPathContext.newContext(null);
	}

	public CheckExpression createCheckExpression(String expression) {
		return new CheckExpression(expression, jxPathContext);
	}

	public ConditionalExpression createConditionalExpression(String expression) {
		return new ConditionalExpression(expression, jxPathContext);
	}

	public DefaultValueExpression createDefaultValueExpression(String expression) {
		return new DefaultValueExpression(expression, jxPathContext);
	}

	public ModelPathExpression createModelPathExpression(String expression) {
		return new ModelPathExpression(expression, jxPathContext);
	}

	public RelevanceExpression createRelevanceExpression(String expression) {
		return new RelevanceExpression(expression, jxPathContext);
	}

	public RequiredExpression createRequiredExpression(String expression) {
		return new RequiredExpression(expression, jxPathContext);
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

}
