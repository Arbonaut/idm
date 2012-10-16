/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.model.BooleanAttribute;
import org.openforis.idm.model.BooleanValue;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 */
public class BooleanAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;
	
	private final FieldDefinition<?>[] FIELD_DEFINITIONS = {
			new FieldDefinition<Boolean>("value", "v", null, Boolean.class, this)
	};
	
	private boolean affirmativeOnly;


	BooleanAttributeDefinition(Survey survey, int id) {
		super(survey, id);
	}

	public boolean isAffirmativeOnly() {
		return affirmativeOnly;
	}
	
	public void setAffirmativeOnly(boolean affirmativeOnly) {
		this.affirmativeOnly = affirmativeOnly;
	}

	@Override
	public Node<?> createNode() {
		return new BooleanAttribute(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public BooleanValue createValue(String string) {
		return new BooleanValue(string);
	}
	
	@Override
	public List<FieldDefinition<?>> getFieldDefinitions() {
		return Collections.unmodifiableList(Arrays.asList(FIELD_DEFINITIONS));
	}

	@Override
	public Class<? extends Value> getValueType() {
		return BooleanValue.class;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (affirmativeOnly ? 1231 : 1237);
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
		BooleanAttributeDefinition other = (BooleanAttributeDefinition) obj;
		if (affirmativeOnly != other.affirmativeOnly)
			return false;
		return true;
	}
}
