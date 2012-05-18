package org.openforis.idm.transform;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Node;

public abstract class NodeColumnProvider<N extends Node<D>, D extends NodeDefinition> implements ColumnProvider<N> {
	private D definition;

	public NodeColumnProvider(D definition) {
		this.definition = definition;
	}
	
	public D getDefinition() {
		return definition;
	}
}
