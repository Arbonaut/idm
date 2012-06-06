/**
 * 
 */
package org.openforis.idm.model;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.FieldDefinition;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.metamodel.validation.ValidationResults;
import org.openforis.idm.metamodel.validation.Validator;
import org.openforis.idm.model.expression.internal.MissingValueException;

/**
 * @author M. Togna
 * @author G. Miceli
 */
@SuppressWarnings("rawtypes")
public abstract class Attribute<D extends AttributeDefinition, V extends Value> extends Node<D> {

	private static final long serialVersionUID = 1L;

	private Field[] fields;
	
	private transient ValidationResults validationResults;

	protected Attribute(D definition) {
		super(definition);
		initFields();
	}

	public void clearFieldSymbols() {
		for ( Field<?> field : fields ) {
			field.setSymbol( null );
		}
	} 
	
	public void clearFieldStates() {
		for ( Field<?> field : fields ) {
			field.getState().set(0);
		}
	} 
	
	private void initFields() {
		List<FieldDefinition<?>> fieldsDefinitions = definition.getFieldDefinitions();
		this.fields = new Field[fieldsDefinitions.size()];
		for (int i = 0; i < fieldsDefinitions.size(); i++) {
			FieldDefinition fieldDefn = fieldsDefinitions.get(i);
			this.fields[i] = (Field) fieldDefn.createNode();
			this.fields[i].setAttribute(this);
		}
	}
	
	public Field<?> getField(int idx) {
		return fields[idx];
	}
	
	public Field<?> getField(String name) {
		List<FieldDefinition<?>> fieldsDefinitions = definition.getFieldDefinitions();
		for (int i = 0; i < fieldsDefinitions.size(); i++) {
			FieldDefinition fieldDefn = fieldsDefinitions.get(i);
			if (fieldDefn.getName().equals(name)) {
				return fields[i];
			}
		}
		return null;
	}

	public int getFieldCount() {
		return fields.length;
	}
	
	/**
	 * Reset value and symbol
	 */
	public void clearValue() {
		for (Field<?> field : fields) {
			field.setValue(null);
		}
		onUpdateValue();
	}

	/**
	 * Reset all properties of all attributeFields (remarks, value, symbol)
	 */
	public void clearFields() {
		for (Field<?> field : fields) {
			field.clear();
		}
		onUpdateValue();
	}
	
	/**
	 * @return a non-null, immutable value
	 */
	public abstract V getValue();

	/**
	 * @param value a non-null, immutable value to set
	 */
	public abstract void setValue(V value);
	
	protected void onUpdateValue(){
		if ( !isDetached() ) {
			clearDependencyStates();
		}
	}
	
//	public V getDefaultValue() throws InvalidExpressionException {
//		D definition = getDefinition();
//		List<AttributeDefault> attributeDefaults = definition.getAttributeDefaults();
//		for (AttributeDefault attributeDefault : attributeDefaults) {
//			V value = attributeDefault.evaluate(this);
//			if (value != null) {
//				return value;
//			}
//		}
//		return null;
//	}

	/**
	 * @return true if value is empty (there is not a field with value specified)
	 */
	@Override
	public boolean isEmpty() {
		for (Field<?> field : fields) {
			if ( field.hasValue() ) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return true if all fields are empty and 
	 *  no remarks or symbol are specified
	 */
	@Override
	public boolean hasData() {
		for (Field<?> field : fields) {
			if ( field.hasData() ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if all fields have value specified
	 * @return
	 */
	public boolean isFilled(){
		for ( Field<?> field : fields ) {
			if ( ! field.hasValue() ) {
				return false;
			}
		}
		return true;
	}

//	public boolean isDefaultValue() {
//		return defaultValue;
//	}

//	public void applyDefaultValue() throws InvalidExpressionException {
//		V value = getDefaultValue();
//		if (value != null) {
//			setValue(value);
//			this.defaultValue = true;
//		}
//	}

	@Override
	public void clearDependencyStates() {
		super.clearDependencyStates();
		clearValidationResults();
		Set<Attribute<?, ?>> checkDep = getCheckDependencies();
		for (Attribute<?, ?> attribute : checkDep) {
			attribute.clearValidationResults();
		}
	}
	
	public Set<Attribute<?, ?>> getCheckDependencies() {
		Set<Attribute<?, ?>> attributes = new HashSet<Attribute<?, ?>>();
		Set<NodePathPointer> dependencyPaths = getDefinition().getCheckDependencyPaths();
		for (NodePathPointer npp : dependencyPaths) {
			String path = npp.getEntityPath() + "/" + npp.getChildName() + "[1]";
			try {
				List<Node<?>> nodes = iterateModelPathExpression(this, path);
				for (Node<?> node : nodes) {
					Attribute<?, ?> attribute = (Attribute<?, ?>) node;
					attributes.add(attribute);
				}
			} catch (MissingValueException e) {
				continue;
			}
		}
		return attributes;
	}

	/**
	 * 
	 * @return list of failed validation rules
	 */
	public ValidationResults validateValue() {
		if ( validationResults == null ) {
			SurveyContext recordContext = getContext();
			Validator validator = recordContext.getValidator();
			validationResults = validator.validate(this);
		}
		return validationResults;
	}

	public void clearValidationResults() {
		validationResults = null;
	}
	
	@Override
	protected void write(StringWriter sw, int indent) {
		V value = getValue();
		for (int i = 0; i < indent; i++) {
			sw.append('\t');
		}
		sw.append(getName());
		sw.append(": ");
		sw.append(value == null ? "!!null" : value.toString());
		sw.append("\n");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(fields);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (!Arrays.equals(fields, other.fields))
			return false;
		return true;
	}
}
