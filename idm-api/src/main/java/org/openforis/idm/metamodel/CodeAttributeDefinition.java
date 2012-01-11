/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.xml.InvertBooleanAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "listName", "key", "allowUnlisted", "parentExpression", "relevantExpression", "requiredExpression",
		"multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName", "labels", "prompts", "descriptions", "attributeDefaults", "checks" })
public class CodeAttributeDefinition extends AttributeDefinition  {

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "key")
	private Boolean key;

	@XmlAttribute(name = "strict")
	@XmlJavaTypeAdapter(value = InvertBooleanAdapter.class)
	private Boolean allowUnlisted;

	@XmlAttribute(name = "parent")
	private String parentExpression;

	@XmlTransient
	private CodeList list;

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
			throw new DetachedModelDefinitionException(CodeAttributeDefinition.class, Survey.class);
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
}
