/**
 * 
 */
package org.openforis.idm.model.impl;

import java.util.Iterator;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathIntrospector;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.ModelObjectDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.BooleanValue;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Coordinate;
import org.openforis.idm.model.Expression;
import org.openforis.idm.model.FileValue;
import org.openforis.idm.model.ModelObject;
import org.openforis.idm.model.NumberValue;
import org.openforis.idm.model.NumericRange;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.Taxon;
import org.openforis.idm.model.TextValue;
import org.openforis.idm.model.TimestampValue;
import org.openforis.idm.model.Value;

/**
 * @author M. Togna
 * 
 */
public class ExpressionImpl implements Expression {

	private static JXPathContext CONTEXT;

	static {
		JXPathIntrospector.registerDynamicClass(ModelObject.class, ModelPropertyHandler.class);
		JXPathIntrospector.registerDynamicClass(Record.class, RecordPropertyHandler.class);

		CONTEXT = JXPathContext.newContext(null);
//		FunctionLibrary functionLibrary = new FunctionLibrary();
		// functionLibrary.addFunctions(new ClassFunctions(ModelDefinitionFunctions.class, "idm"));
//		CONTEXT.setFunctions(functionLibrary);
	}

	private String expression;

	public ExpressionImpl(String expression) {
		super();
		this.expression = expression;
	}

	@Override
	public Object evaluate(Record context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evaluate(ModelObject<? extends ModelObjectDefinition> context) {

		Iterator<?> iterator = Iterate(context);
		if (iterator.hasNext()) {
			Object object = iterator.next();
			return object;
		}
		return Boolean.FALSE;
	}

	@Override
	public Iterator<?> Iterate(ModelObject<? extends ModelObjectDefinition> context) {
		ModelObject<? extends ModelObjectDefinition> parent = context.getParent();
		JXPathContext jxPathContext = JXPathContext.newContext(CONTEXT, parent);
		
		if (context instanceof Attribute) {
			@SuppressWarnings("unchecked")
			Object value = getValue((Attribute<? extends AttributeDefinition, ? extends Value>) context);
			jxPathContext.getVariables().declareVariable("this", value);
		}
		
		String expr = this.expression.replaceAll("\\bparent\\(\\)", "__parent");
		Iterator<?> iterator = jxPathContext.iterate(expr);
		return iterator;
	}

	private Object getValue(Attribute<? extends AttributeDefinition, ? extends Value> attribute) {
		Object value = null;
		if (attribute instanceof Code) {
			value = ((Code<?>) attribute).getCode();
		} else if (attribute instanceof BooleanValue) {
			value = ((BooleanValue) attribute).getBoolean();
		} else if (attribute instanceof TimestampValue) {
			value = ((TimestampValue) attribute).toCalendar();
		} else if (attribute instanceof FileValue) {
			value = attribute;
		} else if (attribute instanceof NumericRange) {
			value = attribute;
		} else if (attribute instanceof NumberValue) {
			value = ((NumberValue<?>) attribute).getNumber();
		} else if (attribute instanceof Taxon) {
			value = attribute;
		} else if (attribute instanceof TextValue) {
			value = ((TextValue) attribute).getString();
		} else if (attribute instanceof Coordinate) {
			value = attribute;
		}
		return value;
	}

}
