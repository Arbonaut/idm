package org.openforis.idm.metamodel;

import java.util.List;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface EntityDefinition extends ModelObjectDefinition {

	/**
	 * @return    Returns the children.
	 * @uml.property   name="childDefinitions"
	 * @uml.associationEnd   multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite" inverse="parent:org.openforis.idm.metamodel.SchemaObjectDefinition"
	 */
	public List<ModelObjectDefinition> getChildDefinitions();

	/**
	 * Setter of the property <tt>children</tt>
	 * @param children  The children to set.
	 * @uml.property  name="childDefinitions"
	 */
	public void setChildDefinitions(List<ModelObjectDefinition> childDefinitions);

}
