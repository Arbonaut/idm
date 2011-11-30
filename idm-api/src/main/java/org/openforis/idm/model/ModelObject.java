package org.openforis.idm.model;

import java.util.List;

import org.openforis.idm.metamodel.Check;
import org.openforis.idm.metamodel.ModelObjectDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ModelObject<D extends ModelObjectDefinition, T> {
	/**
	 * @return Returns the parent.
	 * @uml.property name="record" readOnly="true"
	 * @uml.associationEnd inverse="children:org.openforis.idm.model.Record"
	 */
	Record getRecord();

	/**
	 * @return Returns the parent.
	 * @uml.property name="parent" readOnly="true"
	 * @uml.associationEnd inverse="children:org.openforis.idm.model.Entity"
	 */
	Entity getParent();

	/**
	 * @return Returns the definition.
	 * @uml.property name="definition" readOnly="true"
	 */
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

	List<T> toList();

	T get(int index);

	boolean add(T e, ModelObjectVisitor visitor);

	void add(int index, T element, ModelObjectVisitor visitor);

	T remove(int index, ModelObjectVisitor visitor);

	void clear(ModelObjectVisitor visitor);

	T set(int index, T element, ModelObjectVisitor visitor);
}
