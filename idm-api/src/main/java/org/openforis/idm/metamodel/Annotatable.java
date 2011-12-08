package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Annotatable {

	/**
	 * @return Returns the annotations.
	 * @uml.property name="annotations"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="annotatable:org.openforis.idm.metamodel.ModelAnnotation" readOnly="true"
	 */
	public List<ModelAnnotation> getAnnotations();

}
