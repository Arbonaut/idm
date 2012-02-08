/**
 * 
 */
package org.openforis.idm.validation;

import java.util.ArrayList;
import java.util.List;


/**
 * @author G. Miceli
 * @author M. Togna
 *
 */
public class ValidationResults {

	private List<RuleFailure> errors;
	private List<RuleFailure> warnings;
	
	public ValidationResults() {
		errors = new ArrayList<RuleFailure>();
		warnings =new ArrayList<RuleFailure>();
	}

	public List<RuleFailure> getErrors(){
		return errors;
	}

	public List<RuleFailure> getWarnings(){
		return warnings;
	}
	
	public List<RuleFailure> getFailures(){
		List<RuleFailure> failures = new ArrayList<RuleFailure>();
		failures.addAll(getErrors());
		failures.addAll(getWarnings());
		return failures;
	}
	
	public void addError(RuleFailure failure){
		getErrors().add(failure);
	}
	
	public void addWarning(RuleFailure failure){
		getWarnings().add(failure);
	}
	
}
