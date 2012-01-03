package org.openforis.idm.model;

import java.util.List;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;

/**
 * NOTE: METHODS ARE DRAFT; TO BE IMPLEMENTED AS NEEDED. UNUSED METHODS WILL BE REMOVED.
 * 
 * @author G. Miceli
 * @author M. Togna
 */
public interface Entity extends Node<EntityDefinition> {

	Node<? extends NodeDefinition> get(String name, int index);

	/**
	 * @return Immutable list containing all children with the specified name (entities and attributes), or an empty list if none exist.
	 */
	// List<Node<?>> get(String name);

	int getCount(String name);

	void move(String name, int oldIndex, int newIndex);

	void add(Node<? extends NodeDefinition> o);

	void add(Node<? extends NodeDefinition> o, int index);

	Node<? extends NodeDefinition> remove(String name, int index);

	// Node<?> remove(Node<?> o);

	// void remove(String name);

	// void clear();

	// Node<?> replace(Node<?> o, int index);
	


	// DERIVED STATES //

	List<RuleFailure> getErrors(String name);

	List<RuleFailure> getWarnings(String name);

	boolean hasErrors(String name);

	boolean hasWarnings(String name);

	boolean isRequired(String name);

	boolean isRelevant(String name);

}
