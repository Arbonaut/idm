/**
 * 
 */
package org.openforis.idm.model;

import java.util.Iterator;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathIntrospector;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.SchemaObjectDefinition;
import org.openforis.idm.model.impl.jxpath.ModelPropertyHandler;
import org.openforis.idm.model.impl.jxpath.RecordPropertyHandler;

/**
 * @author M. Togna
 * 
 */
public class ModelExpression implements Expression {

	private static JXPathContext CONTEXT;

	static {
		JXPathIntrospector.registerDynamicClass(ModelObject.class, ModelPropertyHandler.class);
		JXPathIntrospector.registerDynamicClass(Record.class, RecordPropertyHandler.class);

		CONTEXT = JXPathContext.newContext(null);
		// FunctionLibrary functionLibrary = new FunctionLibrary();
		// functionLibrary.addFunctions(new ClassFunctions(ModelDefinitionFunctions.class, "idm"));
		// CONTEXT.setFunctions(functionLibrary);
	}

	private String expression;

	public ModelExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public Object evaluate(Record context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evaluate(ModelObject<? extends SchemaObjectDefinition> context) {

		Iterator<?> iterator = Iterate(context);
		if (iterator.hasNext()) {
			Object object = iterator.next();
			return object;
		}
		return Boolean.FALSE;
	}

	@Override
	public Iterator<?> Iterate(ModelObject<? extends SchemaObjectDefinition> context) {
		ModelObject<? extends SchemaObjectDefinition> parent = context.getParent();
		JXPathContext jxPathContext = JXPathContext.newContext(CONTEXT, parent);

		if (context instanceof Attribute) {
			@SuppressWarnings("unchecked")
			Object value = getValue((Attribute<? extends AttributeDefinition, ?>) context);
			jxPathContext.getVariables().declareVariable("this", value);
		}

		String expr = this.expression.replaceAll("\\bparent\\(\\)", "__parent");
		Iterator<?> iterator = jxPathContext.iterate(expr);
		return iterator;
	}
	
	private Object getValue(Attribute<? extends AttributeDefinition, ?> attribute) {
		// TODO implement getValue, possibly with subcomponent as parameter?  should this be getMainValue or similar?
		/*
		Object value = null;
		if (attribute instanceof Code) {
			value = ((Code<?>) attribute).getCode();
		} else if (attribute instanceof BooleanValue) {
			value = ((BooleanValue) attribute).getBoolean();
		} else if (attribute instanceof TimestampValue) {
			// TODO Format to String
			value = ((TimestampValue) attribute).toCalendar();
		} else if (attribute instanceof File) {
			value = attribute;
		} else if (attribute instanceof NumericRange) {
			value = attribute;
		} else if (attribute instanceof NumericValue) {
			value = ((NumericValue<?>) attribute).getNumber();
		} else if (attribute instanceof Taxon) {
			value = attribute;
		} else if (attribute instanceof TextValue) {
			value = ((TextValue) attribute).getString();
		} else if (attribute instanceof Coordinate) {
			value = attribute;
		}
		return value;
		 */
		return null;
	}

}
