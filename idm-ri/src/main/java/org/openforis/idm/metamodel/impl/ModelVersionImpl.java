/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.ModelVersion;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "labels", "descriptions", "date" })
public class ModelVersionImpl implements ModelVersion {

	@XmlAttribute(name = "name")
	private String name;

	@XmlElement(name = "date")
	private Date date;

	@XmlElement(name = "label", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> labels;

	@XmlElement(name = "description", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> descriptions;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<LanguageSpecificText> getLabels() {
		return this.labels;
	}

	@Override
	public void setLabels(List<LanguageSpecificText> labels) {
		this.labels = labels;
	}

	@Override
	public List<LanguageSpecificText> getDescriptions() {
		return this.descriptions;
	}

	@Override
	public void setDescriptions(List<LanguageSpecificText> descriptions) {
		this.descriptions = descriptions;
	}

	@Override
	public Date getDate() {
		return this.date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

}
