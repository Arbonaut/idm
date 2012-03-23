/**
 * 
 */
package org.openforis.idm.model;


/**
 * @author M. Togna
 * 
 */
public class Validator {

	static {
		registerDependencies();
	}

	// @SuppressWarnings("unchecked")
	// public void validate(Node<? extends NodeDefinition> node, boolean validateChildren, boolean validateDependant) {
	// if (node instanceof Attribute) {
	// validate((Attribute<? extends AttributeDefinition, ? extends Value>) node, validateDependant);
	// } else if (node instanceof Entity) {
	// validate((Entity) node, validateChildren, validateDependant);
	// }
	// }
/*
	public void validate(Attribute<? extends AttributeDefinition, ? extends AbstractValue> attribute, boolean validateDependant) {
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
	public void validate(Entity entity, boolean validateChildren, boolean validateDependant) {

	}

	public void validate(Record record) {
		Entity rootEntity = (Entity) record.getRootEntity();
		validate(rootEntity, Boolean.TRUE, Boolean.FALSE);
	}

	private static void registerDependencies() {
		// TODO Auto-generated method stub

	}
}
