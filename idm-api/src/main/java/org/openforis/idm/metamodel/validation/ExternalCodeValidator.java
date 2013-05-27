/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeListLevel;
import org.openforis.idm.metamodel.ExternalCodeListItem;
import org.openforis.idm.metamodel.ExternalCodeListProvider;
import org.openforis.idm.metamodel.StringKeyValuePair;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.CodeAttribute;

/**
 * @author M. Togna
 * 
 */
public class ExternalCodeValidator implements ValidationRule<CodeAttribute> {

	@Override
	public ValidationResultFlag evaluate(CodeAttribute codeAttribute) {
		List<StringKeyValuePair> filters = new ArrayList<StringKeyValuePair>();
		CodeAttribute codeParent = codeAttribute.getCodeParent();
		int level = 0;
		while (codeParent != null) {
			String colName = getLevelName(codeParent);
			String codeValue = getCodeValue(codeParent);
			filters.add(new StringKeyValuePair(colName, codeValue));
			codeParent = codeParent.getCodeParent();
			level ++;
		}
		String levelName = getLevelName(codeAttribute);
		String codeValue = getCodeValue(codeAttribute);
		filters.add(new StringKeyValuePair(levelName, codeValue));
		fillEmptyColumnValues(codeAttribute, filters, level);
		
		ExternalCodeListProvider externalCodeListProvider = getExternalCodeListProvider(codeAttribute);
		CodeList list = getList(codeAttribute);
		StringKeyValuePair[] filtersArray = (StringKeyValuePair[]) filters.toArray(new StringKeyValuePair[] {});
		ExternalCodeListItem item = externalCodeListProvider.getItem(codeAttribute);
		if (item == null || item.getCode() == null || !item.getCode().equals(codeAttribute.getValue().getCode())) {
			if ( isAllowedUnlisted(codeAttribute) ) {
				return ValidationResultFlag.WARNING;
			} else {
				return ValidationResultFlag.ERROR;
			}
		} else {
			return ValidationResultFlag.OK;
		}
	}

	private void fillEmptyColumnValues(CodeAttribute codeAttribute, List<StringKeyValuePair> filters, int level) {
		CodeAttributeDefinition definition = codeAttribute.getDefinition();
		CodeList codeList = definition.getList();
		List<CodeListLevel> hierarchy = codeList.getHierarchy();
		for(int i = level+1; i<hierarchy.size(); i++){
			CodeListLevel codeListLevel = hierarchy.get(i);
			String name = codeListLevel.getName();
			filters.add(new StringKeyValuePair(name, ""));
		}
	}

	private CodeList getList(CodeAttribute codeAttribute) {
		CodeAttributeDefinition definition = codeAttribute.getDefinition();
		CodeList list = definition.getList();
		return list;
	}

	private ExternalCodeListProvider getExternalCodeListProvider(CodeAttribute codeAttribute) {
		Survey survey = codeAttribute.getSurvey();
		SurveyContext surveyContext = survey.getContext();
		return surveyContext.getExternalCodeListProvider();
	}

	private String getCodeValue(CodeAttribute codeAttribute) {
		Code code = codeAttribute.getValue();
		return code == null ? "" : code.getCode();
	}

	private String getLevelName(CodeAttribute codeAttribute) {
		CodeAttributeDefinition defn = codeAttribute.getDefinition();
		CodeList list = defn.getList();
		List<CodeListLevel> hierarchy = list.getHierarchy();
		int levelIdx = defn.getCodeListLevelIndex();
		CodeListLevel level = hierarchy.get(levelIdx - 1);
		return level.getName();
	}
	
	private boolean isAllowedUnlisted(CodeAttribute attribute) {
		CodeAttributeDefinition definition = attribute.getDefinition();
		return definition.isAllowUnlisted();
	}

}
