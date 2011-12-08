package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Schema extends ModelObjectDefinitionContainer {

	/**
	 * @return Returns the rootEntityDefinitions.
	 * @uml.property name="rootEntityDefinitions"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="schema:org.openforis.idm.metamodel.EntityDefinition" readOnly="true"
	 */
	List<EntityDefinition> getRootEntityDefinitions();
}
