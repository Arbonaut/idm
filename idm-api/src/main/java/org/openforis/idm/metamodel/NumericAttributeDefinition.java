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
	 * @return Returns the precisionDefinitions.
	 * @uml.property name="precisionDefinitions"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="numericAttributeDefinition:org.openforis.idm.metamodel.Precision"
	 */
	public List<Precision> getPrecisionDefinitions();

	/**
	 * @return Returns the type.
	 * @uml.property name="type"
	 */
	public Type getType();

}
