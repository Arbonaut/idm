/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.model.BooleanAttribute;
import org.openforis.idm.model.BooleanValue;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"id", "name", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName", 
		"affirmativeOnly", "labels", "prompts", "descriptions", "attributeDefaults", "checks" })
public class BooleanAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;
	
	@XmlTransient
	private final FieldDefinition<?>[] FIELD_DEFINITIONS = {
			new FieldDefinition<Boolean>("value", "v", null, Boolean.class, this)
	};
	
	@XmlAttribute(name = "affirmativeOnly")
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
}
