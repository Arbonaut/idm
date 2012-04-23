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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.xml.internal.InvertBooleanAdapter;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.CodeAttribute;
import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "listName", "key", "allowUnlisted", "parentExpression", "relevantExpression","required", "requiredExpression",
		"multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName", "labels", "prompts", "descriptions", "attributeDefaults", "checks" })
public class CodeAttributeDefinition extends AttributeDefinition implements KeyAttributeDefinition  {

	private static final long serialVersionUID = 1L;
	
	static List<FieldDefinition> fieldsDefinitions = Collections.unmodifiableList(Arrays.asList(
		new FieldDefinition("code", "c", String.class), 
		new FieldDefinition("qualifier", "q", String.class)
	));
	
	@XmlAttribute(name = "key")
	private Boolean key;

	@XmlAttribute(name = "strict")
	@XmlJavaTypeAdapter(value = InvertBooleanAdapter.class)
	private Boolean allowUnlisted;

	@XmlAttribute(name = "parent")
	private String parentExpression;

	@XmlTransient
	private CodeList list;

	@XmlTransient
	private CodeAttributeDefinition parentCodeAttributeDefinition; 
	
	public CodeList getList() {
		return this.list;
	}

	protected void setList(CodeList list) {
		this.list = list;
	}
	
	@XmlAttribute(name = "list")
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
	public List<FieldDefinition> getFieldsDefinitions() {
		return fieldsDefinitions;
	}
	
}
