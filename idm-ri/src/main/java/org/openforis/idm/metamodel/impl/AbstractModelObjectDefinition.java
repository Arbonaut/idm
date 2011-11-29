/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.CardinalityCheck;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.ModelAnnotation;
import org.openforis.idm.metamodel.ModelObjectDefinition;
import org.openforis.idm.metamodel.ModelObjectLabel;
import org.openforis.idm.metamodel.ModelVersion;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public abstract class AbstractModelObjectDefinition implements ModelObjectDefinition {

	@XmlAttribute(name = "since")
	String sinceAttribute;
	@XmlTransient
	private ModelVersion since;

	@XmlAttribute(name = "deprecated")
	String deprecatedAttribute;
	@XmlTransient
	private ModelVersion deprecated;

	@XmlTransient
	private EntityDefinition parentDefinition;

	@XmlAttribute(name = "required")
	String requiredExpression;
	@XmlAttribute(name = "minCount")
	Integer minCount;
	@XmlAttribute(name = "maxCount")
	Integer maxCount;
	@XmlTransient
	private CardinalityCheck cardinalityCheck;

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "relevant")
	private String relevantExpression;

	@XmlElement(name = "description", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> descriptions;

	@XmlAttribute(name = "multiple")
	private boolean multiple;

	@XmlTransient
	private List<ModelAnnotation> annotations;

	@XmlElement(name = "label", type = ModelObjectLabelImpl.class)
	private List<ModelObjectLabel> labels;

	public AbstractModelObjectDefinition() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openforis.idm.metamodel.ModelObjectDefinitionContainer#get(java.lang.String)
	 */
	@Override
	public ModelObjectDefinition get(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityDefinition getParentDefinition() {
		return this.parentDefinition;
	}

	void setParentDefinition(EntityDefinition parentDefinition) {
		this.parentDefinition = parentDefinition;
	}

	@Override
	public CardinalityCheck getCardinalityCheck() {
		return this.cardinalityCheck;
	}

	void setCardinalityCheck(CardinalityCheck cardinalityCheck) {
		this.cardinalityCheck = cardinalityCheck;
	}

	@Override
	public ModelVersion getSince() {
		return this.since;
	}

	@Override
	public void setSince(ModelVersion since) {
		this.since = since;
	}

	@Override
	public ModelVersion getDeprecated() {
		return this.deprecated;
	}

	@Override
	public void setDeprecated(ModelVersion deprecated) {
		this.deprecated = deprecated;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getRelevantExpression() {
		return this.relevantExpression;
	}

	@Override
	public void setRelevantExpression(String relevantExpression) {
		this.relevantExpression = relevantExpression;
	}

	@Override
	public boolean isMultiple() {
		return this.multiple;
	}

	@Override
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	@Override
	public List<ModelAnnotation> getAnnotations() {
		return this.annotations;
	}

	@Override
	public void setAnnotations(List<ModelAnnotation> annotations) {
		this.annotations = annotations;
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
	public List<ModelObjectLabel> getLabels() {
		return this.labels;
	}

	@Override
	public void setLabels(List<ModelObjectLabel> labels) {
		this.labels = labels;
	}

}
