/**
 * 
 */
package org.openforis.idm.validation;

import org.openforis.idm.metamodel.Check;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Node;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public class CheckResult implements RuleResult {

	private Check check;
	private boolean passed;
	private Attribute<?, ?> attribute;

	public CheckResult(Attribute<?, ?> attribute, Check check, boolean passed) {
		this.attribute = attribute;
		this.check = check;
		this.passed = passed;
	}

	public Check getCheck() {
		return check;
	}

	public Check.Flag getFlag() {
		if (check.getFlag() != null) {
			return check.getFlag();
		}
		return Check.Flag.ERROR;
	}

	@Override
	public boolean isPassed() {
		return passed;
	}

	@Override
	public Node<?> getNode() {
		return attribute;
	}

	public boolean isError() {
		return check.getFlag().equals(Check.Flag.ERROR);
	}

	public boolean isWarning() {
		return check.getFlag().equals(Check.Flag.WARN);
	}
}
