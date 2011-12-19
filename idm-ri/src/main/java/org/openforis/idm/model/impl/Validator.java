/**
 * 
 */
package org.openforis.idm.model.impl;


/**
 * @author M. Togna
 * 
 */
public class Validator {

	static {
		registerDependencies();
	}

	// @SuppressWarnings("unchecked")
	// public void validate(AbstractModelObject<? extends ModelObjectDefinition> modelObject, boolean validateChildren, boolean validateDependant) {
	// if (modelObject instanceof DefaultAttribute) {
	// validate((DefaultAttribute<? extends AttributeDefinition, ? extends Value>) modelObject, validateDependant);
	// } else if (modelObject instanceof DefaultEntity) {
	// validate((DefaultEntity) modelObject, validateChildren, validateDependant);
	// }
	// }
/*
	public void validate(DefaultAttribute<? extends AttributeDefinition, ? extends AbstractValue> attribute, boolean validateDependant) {
		Value value = attribute.getValue();
		if (!value.isBlank()) {
			if (!value.isFormatValid()) {
				//attribute.addError(new CheckFailureImpl(new ValueFormatCheck()));
			}
			// data type?
			else {
				List<Check> checks = attribute.getDefinition().getChecks();
				if (checks != null) {
					for (Check check : checks) {
						boolean valid = executeCheck(check, value);
						if (!valid) {
							if (check.getFlag().equals(Check.Flag.ERROR)) {
								attribute.addError(new CheckFailureImpl(check));
							} else if (check.getFlag().equals(Check.Flag.WARN)) {
								attribute.addWarning(new CheckFailureImpl(check));
							}
						}
					}
				}
			}
		}
	}
	*/
/*
	private boolean executeCheck(Check check, AbstractValue value) {
		if (value != null) {
			if (check instanceof CustomCheck) {

			} else if (check instanceof CustomCheck) {

			} else if (check instanceof DistanceCheck) {

			} else if (check instanceof UniquenessCheck) {

			} else if (check instanceof PatternCheck) {

			} else if (check instanceof ComparisonCheck) {

			}
		}
		return true;
	}
*/
	public void validate(DefaultEntity entity, boolean validateChildren, boolean validateDependant) {

	}

	public void validate(DefaultRecord record) {
		DefaultEntity rootEntity = (DefaultEntity) record.getRootEntity();
		validate(rootEntity, Boolean.TRUE, Boolean.FALSE);
	}

	private static void registerDependencies() {
		// TODO Auto-generated method stub

	}
}
