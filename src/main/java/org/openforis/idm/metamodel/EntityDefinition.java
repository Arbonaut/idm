package org.openforis.idm.metamodel;

import java.util.List;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface EntityDefinition extends ModelObjectDefinition {


	/**
	 * @return    Returns the schema.
	 * @uml.property   name="schema"
	 * @uml.associationEnd   inverse="rootEntityDefinitions:org.openforis.idm.metamodel.Schema"
	 */
	public Schema getSchema();

	/**
	 * Setter of the property <tt>schema</tt>
	 * @param schema  The schema to set.
	 * @uml.property  name="schema"
	 */
	public void setSchema(Schema schema);

	/**
	 * @return    Returns the children.
	 * @uml.property   name="children"
	 * @uml.associationEnd   multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite" inverse="parent:org.openforis.idm.metamodel.SchemaObjectDefinition"
	 */
	public List<ModelObjectDefinition> getChildren();

	/**
	 * Setter of the property <tt>children</tt>
	 * @param children  The children to set.
	 * @uml.property  name="children"
	 */
	public void setChildren(List<ModelObjectDefinition> children);

}
