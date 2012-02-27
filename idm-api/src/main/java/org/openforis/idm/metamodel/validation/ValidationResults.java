/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.metamodel.validation.Check.Flag;
import org.openforis.idm.model.Node;
import org.openforis.idm.util.CollectionUtil;

/**
 * The results of validating a single node
 * 
 * @author G. Miceli
 * @author M. Togna
 * 
 */
public class ValidationResults {

	private List<ValidationResult> passed;
	private List<ValidationResult> errors;
	private List<ValidationResult> warnings;
	
	public ValidationResults() {
		passed = new ArrayList<ValidationResult>();
		errors = new ArrayList<ValidationResult>();
		warnings = new ArrayList<ValidationResult>();
	}

	public List<ValidationResult> getErrors() {
		return CollectionUtil.unmodifiableList(errors);
	}

	public List<ValidationResult> getWarnings() {
		return CollectionUtil.unmodifiableList(warnings);
	}

	public List<ValidationResult> getPassed() {
		return CollectionUtil.unmodifiableList(passed);
	}

	public List<ValidationResult> getFailed() {
		List<ValidationResult> failed = new ArrayList<ValidationResult>(errors.size() + warnings.size());
		failed.addAll(errors);
		failed.addAll(warnings);
		return failed;
	}
	
	public void addResults(ValidationResults other){
		errors.addAll(other.errors);
		warnings.addAll(other.warnings);
		passed.addAll(other.passed);
	}
	
	public void addResult(Node<?> node, ValidationRule validator, boolean result) {
		ValidationResult r = new ValidationResult(node, validator, result);
		if (result) {
			passed.add(r);
		} else if (validator instanceof Check) {
			Flag flag = ((Check) validator).getFlag();
			if ( flag == Flag.ERROR ) {
				errors.add(r);
			} else {
				warnings.add(r);
			}
		}else if (validator instanceof CodeParentValidator){
			warnings.add(r);
		} else {
			errors.add(r);
		}
	}


}
