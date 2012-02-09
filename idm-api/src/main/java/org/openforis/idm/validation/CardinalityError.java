package org.openforis.idm.validation;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class CardinalityError implements RuleFailure {
	
	public enum Reason {
		MIN_COUNT, MAX_COUNT, REQUIRED
	}

	private NodeDefinition nodeDefinition;
	private Reason reason;

	public CardinalityError(NodeDefinition nodeDefinition, Reason reason) {
		this.nodeDefinition = nodeDefinition;
		this.reason = reason;
	}

	public NodeDefinition getNodeDefinition() {
		return nodeDefinition;
	}

	public Reason getReason() {
		return reason;
	}

}
