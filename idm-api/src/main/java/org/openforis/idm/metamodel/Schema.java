package org.openforis.idm.metamodel;

import java.util.List;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Schema {

	/**
	 * @return  Returns the model.
	 * @uml.property  name="model"
	 * @uml.associationEnd  inverse="schema:org.openforis.idm.metamodel.Model"
	 */
	public Model getModel();

//	/**
//	 * Setter of the property <tt>model</tt>
//	 * @param model  The model to set.
//	 * @uml.property  name="model"
//	 */
//	public void setModel(Model model);

	/**
	 * @return  Returns the rootEntityDefinitions.
	 * @uml.property  name="rootEntityDefinitions"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite" inverse="schema:org.openforis.idm.metamodel.EntityDefinition"
	 */
	public List<EntityDefinition> getRootEntityDefinitions();

	/**
	 * Setter of the property <tt>rootEntityDefinitions</tt>
	 * @param rootEntityDefinitions  The rootEntityDefinitions to set.
	 * @uml.property  name="rootEntityDefinitions"
	 */
	public void setRootEntityDefinitions(List<EntityDefinition> rootEntityDefinitions);

}
