/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openforis.idm.metamodel.CardinalityCheck;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.LabelType;
import org.openforis.idm.metamodel.ModelAnnotation;
import org.openforis.idm.metamodel.ModelObjectDefinition;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.MultilingualStringMap;

/**
 * @author Mino Togna
 * 
 */
public abstract class AbstractModelObjectDefinition implements ModelObjectDefinition {

	private ModelVersion since;
	private ModelVersion deprecated;
	private EntityDefinition parentDefinition;
	private CardinalityCheck cardinalityCheck;
	private String name;
	private String relevantExpression;
	private MultilingualStringMap descriptions;
	private boolean multiple;
	private List<ModelAnnotation> annotations;

	private Map<LabelType, MultilingualStringMap> labelsMap;

	public AbstractModelObjectDefinition() {
		this.labelsMap = new HashMap<LabelType, MultilingualStringMap>();
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

	public EntityDefinition getParentDefinition() {
		return this.parentDefinition;
	}

	public CardinalityCheck getCardinalityCheck() {
		return this.cardinalityCheck;
	}

	@Override
	public MultilingualStringMap getLabels(LabelType type) {
		return this.labelsMap.get(type);
	}

	@Override
	public void setLabels(LabelType type, MultilingualStringMap labels) {
		this.labelsMap.put(type, labels);
	}

	public ModelVersion getSince() {
		return since;
	}

	public void setSince(ModelVersion since) {
		this.since = since;
	}

	public ModelVersion getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(ModelVersion deprecated) {
		this.deprecated = deprecated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelevantExpression() {
		return relevantExpression;
	}

	public void setRelevantExpression(String relevantExpression) {
		this.relevantExpression = relevantExpression;
	}

	public MultilingualStringMap getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(MultilingualStringMap descriptions) {
		this.descriptions = descriptions;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public List<ModelAnnotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<ModelAnnotation> annotations) {
		this.annotations = annotations;
	}

}
