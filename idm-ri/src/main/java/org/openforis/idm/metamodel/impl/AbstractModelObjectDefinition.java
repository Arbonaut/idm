/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.ModelAnnotation;
import org.openforis.idm.metamodel.ModelObjectDefinition;
import org.openforis.idm.metamodel.ModelObjectLabel;
import org.openforis.idm.metamodel.ModelObjectLabel.LabelType;
import org.openforis.idm.metamodel.impl.jxpath.MetaModelExpression;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public abstract class AbstractModelObjectDefinition extends AbstractVersionable implements ModelObjectDefinition {

	@XmlAttribute(name = "since")
	String sinceAttribute;

	@XmlAttribute(name = "deprecated")
	String deprecatedAttribute;

	@XmlTransient
	private EntityDefinition parentDefinition;

	@XmlAttribute(name = "required")
	private String requiredExpression;
	@XmlAttribute(name = "minCount")
	private Integer minCount;
	@XmlAttribute(name = "maxCount")
	private Integer maxCount;

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
		MetaModelExpression expression = new MetaModelExpression(path);
		Object object = expression.evaluate(this);
		if (object instanceof ModelObjectDefinition) {
			return (ModelObjectDefinition) object;
		}
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
	public String getName() {
		return this.name;
	}

	@Override
	public String getRelevantExpression() {
		return this.relevantExpression;
	}

	@Override
	public boolean isMultiple() {
		return this.multiple;
	}

	@Override
	public List<ModelAnnotation> getAnnotations() {
		return this.annotations;
	}

	@Override
	public List<LanguageSpecificText> getDescriptions() {
		return this.descriptions;
	}

	@Override
	public List<ModelObjectLabel> getLabels() {
		return this.labels;
	}

	@Override
	public List<ModelObjectLabel> getLabelsByType(LabelType labelType) {
		List<ModelObjectLabel> list = new ArrayList<ModelObjectLabel>();
		if (this.labels != null) {
			for (ModelObjectLabel label : this.labels) {
				if (label.getLabelType().equals(labelType)) {
					list.add(label);
				}
			}
		}
		return list;
	}

	@Override
	public String getRequiredExpression() {
		return this.requiredExpression;
	}

	@Override
	public Integer getMinCount() {
		return this.minCount;
	}

	@Override
	public Integer getMaxCount() {
		return this.maxCount;
	}

}
