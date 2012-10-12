package org.openforis.idm.model;

/**
 * @author G. Miceli
 */
class NodeWrapper {
	Integer definitionId;
	Node<?> node;

	NodeWrapper() {
	}

	NodeWrapper(Node<?> node) {
		this.node = node;
		this.definitionId = node.definitionId;
	}
}
