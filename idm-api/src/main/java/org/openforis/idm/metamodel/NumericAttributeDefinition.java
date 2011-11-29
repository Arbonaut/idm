package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface NumericAttributeDefinition extends AttributeDefinition {

	
	public enum Type {
		INTEGER, REAL
	}

	/**
	 * @return  Returns the precisionDefinitions.
	 * @uml.property  name="precisionDefinitions"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite" inverse="numericAttributeDefinition:org.openforis.idm.metamodel.Precision"
	 */
	public List<Precision> getPrecisionDefinitions();

	/**
	 * Setter of the property <tt>precisionDefinitions</tt>
	 * @param precisionDefinitions  The precisionDefinitions to set.
	 * @uml.property  name="precisionDefinitions"
	 */
	public void setPrecisionDefinitions(List<Precision> precisionDefinitions);

	/**
	 * @return  Returns the type.
	 * @uml.property  name="type"
	 */
	public Type getType();

	/**
	 * Setter of the property <tt>type</tt>
	 * @param type  The type to set.
	 * @uml.property  name="type"
	 */
	public void setType(Type type);

}
