package org.openforis.idm.validation;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class CarinalityResult implements RuleResult {

	public enum Reason {
		MIN_COUNT, MAX_COUNT, REQUIRED
	}

	private NodeDefinition nodeDefinition;
	private Reason reason;
	private boolean passed;
	private Entity entity;

	public CarinalityResult(Entity entity, NodeDefinition nodeDefinition, Reason reason, boolean passed) {
		this.nodeDefinition = nodeDefinition;
		this.reason = reason;
		this.passed = passed;
	}

	public NodeDefinition getNodeDefinition() {
		return nodeDefinition;
	}

	public Reason getReason() {
		return reason;
	}

	@Override
	public boolean isPassed() {
		return passed;
	}

	@Override
	public Node<?> getNode() {
		return entity;
	}

}
