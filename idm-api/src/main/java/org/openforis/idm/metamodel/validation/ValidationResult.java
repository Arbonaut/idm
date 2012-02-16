package org.openforis.idm.metamodel.validation;

import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class ValidationResult {

	private Node<?> node;
	private Validator<?> validator;
	private boolean valid;
	
	public ValidationResult(Node<?> node, Validator<?> validator, boolean valid) {
		this.node = node;
		this.validator = validator;
		this.valid = valid;
	}

	public Node<?> getNode() {
		return node;
	}

	public Validator<?> getValidator() {
		return validator;
	}

	public boolean isValid() {
		return valid;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(validator);
		sb.append(valid ? " PASSED " : " FAILED ");
		sb.append(node.getPath());
		return sb.toString();
	}
}
