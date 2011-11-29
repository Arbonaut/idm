package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.CardinalityCheck;
import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodingScheme;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.Model;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.Versionable;

/**
 * @author M. Togna
 * 
 */
public class MetaModelUnmarshallerListener extends Unmarshaller.Listener {

	Survey survey;
	CodeList currentCodeList;
	Model model;

	@Override
	public void beforeUnmarshal(Object target, Object parent) {
		if (target instanceof Survey) {
			this.survey = (Survey) target;
		} else if (target instanceof Model) {
			this.model = (Model) target;
		} else if ((target instanceof AbstractModelObjectDefinition) && (parent instanceof EntityDefinition)) {
			((AbstractModelObjectDefinition) target).setParentDefinition((EntityDefinition) parent);
		} else if (target instanceof CodeList) {
			this.currentCodeList = (CodeList) target;
		}
		super.beforeUnmarshal(target, parent);
	}

	@Override
	public void afterUnmarshal(Object target, Object parent) {
		if (target instanceof AbstractModelObjectDefinition) {
			AbstractModelObjectDefinition modelObjectDefinition = (AbstractModelObjectDefinition) target;
			this.setModelVersions(modelObjectDefinition, modelObjectDefinition.sinceAttribute, modelObjectDefinition.deprecatedAttribute);

			if ((modelObjectDefinition.minCount != null) || (modelObjectDefinition.maxCount != null) || StringUtils.isNotBlank(modelObjectDefinition.requiredExpression)) {
				CardinalityCheck cardinalityCheck = new CardinalityCheckImpl();
				if (modelObjectDefinition.minCount != null) {
					cardinalityCheck.setMinCount(modelObjectDefinition.minCount);
				}
				if (modelObjectDefinition.maxCount != null) {
					cardinalityCheck.setMaxCount(modelObjectDefinition.maxCount);
				}
				if (StringUtils.isNotBlank(modelObjectDefinition.requiredExpression)) {
					cardinalityCheck.setRequiredExpression(modelObjectDefinition.requiredExpression);
				}
				modelObjectDefinition.setCardinalityCheck(cardinalityCheck);
			}

			if (parent instanceof EntityDefinition) {
				EntityDefinition parentDefinition = (EntityDefinition) parent;
				modelObjectDefinition.setParentDefinition(parentDefinition);
			}
		}

		if (target instanceof CodeListImpl) {
			CodeListImpl codeList = (CodeListImpl) target;
			this.setModelVersions(codeList, codeList.sinceAttribute, codeList.deprecatedAttribute);
		}

		if (target instanceof CodeListItemImpl) {
			CodeListItemImpl codeListItem = (CodeListItemImpl) target;
			this.setModelVersions(codeListItem, codeListItem.sinceAttribute, codeListItem.deprecatedAttribute);
		}

		if (target instanceof CodeDefinitionImpl) {
			CodeDefinitionImpl codeDefinition = (CodeDefinitionImpl) target;
			if (StringUtils.isNotEmpty(codeDefinition.schemeName)) {
				CodingScheme codingScheme = this.getCodingScheme(codeDefinition.schemeName);
				codeDefinition.setCodingScheme(codingScheme);
			}
		}

		super.afterUnmarshal(target, parent);
	}

	private void setModelVersions(Versionable versionable, String since, String deprecated) {
		if (StringUtils.isNotBlank(since)) {
			ModelVersion version = this.getVersion(since);
			versionable.setSince(version);
		}
		if (StringUtils.isNotBlank(deprecated)) {
			ModelVersion version = this.getVersion(deprecated);
			versionable.setDeprecated(version);
		}
	}

	private CodingScheme getCodingScheme(String name) {
		List<CodingScheme> codingSchemes = this.currentCodeList.getCodingSchemes();
		for (CodingScheme codingScheme : codingSchemes) {
			if (codingScheme.getName().equals(name)) {
				return codingScheme;
			}
		}
		return null;
	}

	private ModelVersion getVersion(String string) {
		List<ModelVersion> versions = this.model.getVersions();
		for (ModelVersion modelVersion : versions) {
			if (modelVersion.getName().equals(string)) {
				return modelVersion;
			}
		}
		return null;
	}

}
