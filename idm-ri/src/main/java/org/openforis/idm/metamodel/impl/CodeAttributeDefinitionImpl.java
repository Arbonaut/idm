/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CodeAttributeDefinitionImpl extends AbstractAttributeDefinition implements CodeAttributeDefinition {

	@XmlAttribute(name = "list")
	String listAttribute;
	@XmlTransient
	private CodeList list;

	@XmlAttribute(name = "key")
	private boolean key;

	@XmlAttribute(name = "strict")
	@XmlJavaTypeAdapter(value = AllowUnlistedAdapter.class)
	private Boolean allowUnlisted;

	@XmlAttribute(name = "parent")
	private String parentExpression;

	@Override
	public CodeList getList() {
		return this.list;
	}

	@Override
	public void setList(CodeList list) {
		this.list = list;
	}

	@Override
	public boolean isKey() {
		return this.key;
	}

	@Override
	public void setKey(boolean key) {
		this.key = key;
	}

	@Override
	public boolean getAllowUnlisted() {
		return this.allowUnlisted;
	}

	@Override
	public void setAllowUnlisted(boolean allowUnlisted) {
		this.allowUnlisted = allowUnlisted;
	}

	private static class AllowUnlistedAdapter extends XmlAdapter<Boolean, Boolean> {

		@Override
		public Boolean unmarshal(Boolean v) throws Exception {
			return !v;
		}

		@Override
		public Boolean marshal(Boolean v) throws Exception {
			return !v;
		}
	}

	@Override
	public String getParentExpression() {
		return this.parentExpression;
	}

	@Override
	public void setParentExpression(String parentExpression) {
		this.parentExpression = parentExpression;
	}
}
