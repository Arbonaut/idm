package org.openforis.idm.model;

import java.util.List;

import org.openforis.idm.metamodel.Check;
import org.openforis.idm.metamodel.ModelObjectDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ModelObject<D extends ModelObjectDefinition> {

	D getDefinition();
	
	/**
	 * @return Returns the relevant.
	 * @uml.property name="relevant" readOnly="true"
	 */
	Boolean isRelevant();

	/**
	 * @return Returns an immutable list of failed checks.
	 */
	List<Check> getFailedChecks();

	List<Check> getErrors();

	List<Check> getWarnings();

	boolean hasErrors();

	boolean hasWarnings();
}
