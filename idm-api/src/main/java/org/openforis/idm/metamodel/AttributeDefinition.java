/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openforis.commons.collection.CollectionUtils;
import org.openforis.idm.metamodel.validation.Check;
import org.openforis.idm.model.NodePathPointer;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 * @author K. Waga
 * 
 */

public abstract class AttributeDefinition extends NodeDefinition {
	
	private static final long serialVersionUID = 1L;
	private List<Check<?>> checks;
	private List<AttributeDefault> attributeDefaults;

	AttributeDefinition(Survey survey, int id) {
		super(survey, id);
	}

	public List<Check<?>> getChecks() {
		return CollectionUtils.unmodifiableList(this.checks);
	}
	
	public void addCheck(Check<?> check) {
		if ( checks == null ) {
			checks = new ArrayList<Check<?>>();
		}
		checks.add(check);
	}
	
	public void removeAllChecks() {
		if ( checks != null ) {
			checks.clear();
		}
	}
	
	public void removeCheck(Check<?> check) {
		checks.remove(check);
	}

	public List<AttributeDefault> getAttributeDefaults() {
		return CollectionUtils.unmodifiableList(this.attributeDefaults);
	}

	public void addAttributeDefault(AttributeDefault def) {
		if ( attributeDefaults == null ) {
			attributeDefaults = new ArrayList<AttributeDefault>();
		}
		attributeDefaults.add(def);
	}
	
	public void removeAllAttributeDefaults() {
		if ( attributeDefaults != null ) {
			attributeDefaults.clear();
		}
	}
	public void removeAttributeDefault(AttributeDefault def) {
		attributeDefaults.remove(def);
	}
	
	public void moveAttributeDefault(AttributeDefault def, int toIndex) {
		CollectionUtils.shiftItem(attributeDefaults, def, toIndex);
	}

	public abstract <V extends Value> V createValue(String string);

	public Set<NodePathPointer> getCheckDependencyPaths() {
		Survey survey = getSurvey();
		return survey.getCheckDependencies(this);
	}
	
	public abstract List<FieldDefinition<?>> getFieldDefinitions();
	
	public FieldDefinition<?> getFieldDefinition(String name) {
		List<FieldDefinition<?>> defns = getFieldDefinitions();
		for (FieldDefinition<?> fieldDefinition : defns) {
			if ( fieldDefinition.getName().equals(name) ) {
				return fieldDefinition;
			}
		}
		return null;
	}

	public abstract Class<? extends Value> getValueType();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attributeDefaults == null) ? 0 : attributeDefaults.hashCode());
		result = prime * result + ((checks == null) ? 0 : checks.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeDefinition other = (AttributeDefinition) obj;
		if (attributeDefaults == null) {
			if (other.attributeDefaults != null)
				return false;
		} else if (!attributeDefaults.equals(other.attributeDefaults))
			return false;
		if (checks == null) {
			if (other.checks != null)
				return false;
		} else if (!checks.equals(other.checks))
			return false;
		return true;
	}

	/**
	 * Build name prefixing all names of ancestor single entities
	 * @return
	 */
	public String getCompoundName() {
		StringBuilder sb = new StringBuilder(getName());
		NodeDefinition ancestor = getParentDefinition();
		while ( ancestor != null && !ancestor.isMultiple() ) {
			sb.insert(0, "_");
			sb.insert(0, ancestor.getName());
			ancestor = ancestor.getParentDefinition();
		}
		return sb.toString();
	}
}
