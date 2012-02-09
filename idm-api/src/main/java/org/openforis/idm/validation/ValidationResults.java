/**
 * 
 */
package org.openforis.idm.validation;

import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 * 
 */
public class ValidationResults {

	private List<RuleResult> errors;
	private List<RuleResult> warnings;

	public ValidationResults() {
		errors = new ArrayList<RuleResult>();
		warnings = new ArrayList<RuleResult>();
	}

	public List<RuleResult> getErrors() {
		return CollectionUtil.unmodifiableList(errors);
	}

	public List<RuleResult> getWarnings() {
		return CollectionUtil.unmodifiableList(warnings);
	}

	public List<RuleResult> getFailures() {
		List<RuleResult> failures = new ArrayList<RuleResult>();
		failures.addAll(getErrors());
		failures.addAll(getWarnings());
		return failures;
	}

	public void addError(RuleResult failure) {
		errors.add(failure);
	}

	public void addWarning(RuleResult failure) {
		warnings.add(failure);
	}

}
