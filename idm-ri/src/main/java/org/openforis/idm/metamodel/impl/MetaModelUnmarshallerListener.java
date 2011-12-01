package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.CardinalityCheck;
import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodingScheme;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.Unit;
import org.openforis.idm.metamodel.Versionable;

/**
 * @author M. Togna
 * 
 */
public class MetaModelUnmarshallerListener extends Unmarshaller.Listener {

	Survey survey;
	CodeList currentCodeList;

	@Override
	public void beforeUnmarshal(Object target, Object parent) {
		if (target instanceof Survey) {
			this.survey = (Survey) target;
		} else if (target instanceof CodeList) {
			this.currentCodeList = (CodeList) target;
		}
		super.beforeUnmarshal(target, parent);
	}

	@Override
	public void afterUnmarshal(Object target, Object parent) {
		if (target instanceof AbstractModelObjectDefinition) {
			AbstractModelObjectDefinition modelObjectDefinition = (AbstractModelObjectDefinition) target;
			this.afterUnmarshallModelObjectDefinition(parent, modelObjectDefinition);
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

		if (target instanceof PrecisionImpl) {
			PrecisionImpl precision = (PrecisionImpl) target;
			if (StringUtils.isNotBlank(precision.unitAttribute)) {
				Unit unit = this.getUnit(precision.unitAttribute);
				precision.setUnit(unit);
			}
		}

		if (target instanceof CodeAttributeDefinitionImpl) {
			CodeAttributeDefinitionImpl codeDef = (CodeAttributeDefinitionImpl) target;
			if (StringUtils.isNotEmpty(codeDef.listAttribute)) {
				CodeList codeList = this.getCodeList(codeDef.listAttribute);
				codeDef.setList(codeList);
			}
		}

		super.afterUnmarshal(target, parent);
	}

	/**
	 * @param parent
	 * @param target
	 */
	private void afterUnmarshallModelObjectDefinition(Object parent, AbstractModelObjectDefinition target) {
		this.setModelVersions(target, target.sinceAttribute, target.deprecatedAttribute);

		if ((target.minCount != null) || (target.maxCount != null) || StringUtils.isNotBlank(target.requiredExpression)) {
			CardinalityCheck cardinalityCheck = new CardinalityCheckImpl();
			if (target.minCount != null) {
				cardinalityCheck.setMinCount(target.minCount);
			}
			if (target.maxCount != null) {
				cardinalityCheck.setMaxCount(target.maxCount);
			}
			if (StringUtils.isNotBlank(target.requiredExpression)) {
				cardinalityCheck.setRequiredExpression(target.requiredExpression);
			}
			target.setCardinalityCheck(cardinalityCheck);
		}

		if (parent instanceof EntityDefinition) {
			EntityDefinition parentDefinition = (EntityDefinition) parent;
			target.setParentDefinition(parentDefinition);
		}
	}

	private CodeList getCodeList(String listName) {
		List<CodeList> codeLists = this.survey.getCodeLists();
		for (CodeList codeList : codeLists) {
			if (codeList.getName().equals(listName)) {
				return codeList;
			}
		}
		return null;
	}

	private Unit getUnit(String unitName) {
		List<Unit> units = this.survey.getUnits();
		for (Unit unit : units) {
			if (unit.getName().equals(unitName)) {
				return unit;
			}
		}
		return null;
	}

	private void setModelVersions(Versionable versionable, String since, String deprecated) {
		if (StringUtils.isNotBlank(since)) {
			ModelVersion version = this.getModelVersion(since);
			versionable.setSince(version);
		}
		if (StringUtils.isNotBlank(deprecated)) {
			ModelVersion version = this.getModelVersion(deprecated);
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

	/**
	 * Returns a ModelVersion
	 * 
	 * @param name
	 * @return
	 */
	private ModelVersion getModelVersion(String name) {
		List<ModelVersion> versions = this.survey.getVersions();
		for (ModelVersion modelVersion : versions) {
			if (modelVersion.getName().equals(name)) {
				return modelVersion;
			}
		}
		return null;
	}

}
