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
	 *                     inverse="annotatable:org.openforis.idm.metamodel.ModelAnnotation"
	 */
	public List<ModelAnnotation> getAnnotations();

	/**
	 * Setter of the property <tt>annotations</tt>
	 * 
	 * @param annotations
	 *            The annotations to set.
	 * @uml.property name="annotations"
	 */
	public void setAnnotations(List<ModelAnnotation> annotations);

}
