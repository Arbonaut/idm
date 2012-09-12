/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;*/
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Transient;

import org.openforis.idm.model.BooleanAttribute;
import org.openforis.idm.model.BooleanValue;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 * @author K. Waga
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Order(attributes={"id", "name", "relevant","required", "requiredIf", "multiple", "minCount", "maxCount", "since", "deprecated", "affirmativeOnly"}, 
	   elements = {"label", "prompt", "description", "default"})
public class BooleanAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;
	
	@Transient
	private final FieldDefinition<?>[] FIELD_DEFINITIONS = {
			new FieldDefinition<Boolean>("value", "v", null, Boolean.class, this)
	};
	
	@Attribute(name = "affirmativeOnly", required=false)
	private Boolean affirmativeOnly;

	public boolean isAffirmativeOnly() {
		return affirmativeOnly == null ? false : affirmativeOnly;
	}
	
	protected void setAffirmativeOnly(boolean affirmativeOnly) {
		this.affirmativeOnly = affirmativeOnly ? true : null;
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
		result = prime * result + ((affirmativeOnly == null) ? 0 : affirmativeOnly.hashCode());
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
		if (affirmativeOnly == null) {
			if (other.affirmativeOnly != null)
				return false;
		} else if (!affirmativeOnly.equals(other.affirmativeOnly))
			return false;
		return true;
	}
	
}
