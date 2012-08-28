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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;*/
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Transient;
import org.simpleframework.xml.convert.Convert;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.xml.internal.InvertBooleanAdapter;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.CodeAttribute;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Order(attributes="", elements = {"id", "name", "listName", "key", "allowUnlisted", "parentExpression", "relevantExpression","required", "requiredExpression",
		"multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName", "labels", "prompts", "descriptions", "attributeDefaults", "checks" })
public class CodeAttributeDefinition extends AttributeDefinition implements KeyAttributeDefinition  {

	private static final long serialVersionUID = 1L;
	
	@Transient
	private final FieldDefinition<?>[] FIELD_DEFINITIONS = {
		new FieldDefinition<String>("code", "c", null, String.class, this), 
		new FieldDefinition<String>("qualifier", "q", "other", String.class, this)
	};
	
	@Attribute(name = "key")
	private Boolean key;

	@Attribute(name = "strict")
	@Convert(InvertBooleanAdapter.class)
	private Boolean allowUnlisted;

	@Attribute(name = "parent")
	private String parentExpression;

	@Transient
	private CodeList list;

	@Transient
	private CodeAttributeDefinition parentCodeAttributeDefinition; 
	
	public CodeList getList() {
		return this.list;
	}

	protected void setList(CodeList list) {
		this.list = list;
	}
	
	@Attribute(name = "list")
	public String getListName() {
		return list == null ? null : list.getName();
	}
	
	protected void setListName(String name) {
		Survey survey = getSurvey();
		if ( survey == null ) {
			throw new DetachedNodeDefinitionException(CodeAttributeDefinition.class, Survey.class);
		}
		CodeList newList = survey.getCodeList(name);
		if ( newList == null ) {
			throw new IllegalArgumentException("Code list '"+name+"' not defined");
		}
		this.list = newList;
	}
	
	public boolean isKey() {
		return this.key == null ? false : key;
	}

	public boolean isAllowUnlisted() {
		return allowUnlisted == null ? false : allowUnlisted;
	}
	
	public String getParentExpression() {
		return this.parentExpression;
	}

	public CodeAttributeDefinition getParentCodeAttributeDefinition() {
		if (StringUtils.isNotBlank(parentExpression) && parentCodeAttributeDefinition == null) {
			NodeDefinition parentDefinition = getParentDefinition();
			parentCodeAttributeDefinition = (CodeAttributeDefinition) parentDefinition.getDefinitionByRelativePath(parentExpression);
		}
		return parentCodeAttributeDefinition;
	}

	@Override
	public Node<?> createNode() {
		return new CodeAttribute(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Code createValue(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			return new Code(string);
		}
	}
	
	@Override
	public List<FieldDefinition<?>> getFieldDefinitions() {
		return Collections.unmodifiableList(Arrays.asList(FIELD_DEFINITIONS));
	}

	@Override
	public Class<? extends Value> getValueType() {
		return Code.class;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((allowUnlisted == null) ? 0 : allowUnlisted.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + ((parentExpression == null) ? 0 : parentExpression.hashCode());
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
		CodeAttributeDefinition other = (CodeAttributeDefinition) obj;
		if (allowUnlisted == null) {
			if (other.allowUnlisted != null)
				return false;
		} else if (!allowUnlisted.equals(other.allowUnlisted))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (parentExpression == null) {
			if (other.parentExpression != null)
				return false;
		} else if (!parentExpression.equals(other.parentExpression))
			return false;
		return true;
	}
	
}
