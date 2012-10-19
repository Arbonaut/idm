/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeListLevel;
import org.openforis.idm.metamodel.ExternalCodeListProvider;
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
		List<String> columns = new ArrayList<String>();
		CodeAttribute codeParent = codeAttribute.getCodeParent();
		int level = 0;
		while (codeParent != null) {
			String colName = getColumnName(codeParent);
			columns.add(colName);
			String codeValue = getCodeValue(codeParent);
			columns.add(codeValue);

			codeParent = codeParent.getCodeParent();
			level ++;
		}
		String colName = getColumnName(codeAttribute);
		columns.add(colName);
		String codeValue = getCodeValue(codeAttribute);
		columns.add(codeValue);
		fillEmptyColumnValues(codeAttribute, columns, level);
		
		
		ExternalCodeListProvider externalCodeListProvider = getExternalCodeListProvider(codeAttribute);
		String listName = getListName(codeAttribute);
		String code = externalCodeListProvider.getCode(listName, colName, (Object[]) columns.toArray(new String[] {}));
		if (code == null || !code.equals(codeAttribute.getValue().getCode())) {
			return ValidationResultFlag.ERROR;
		} else {
			return ValidationResultFlag.OK;
		}
	}

	private void fillEmptyColumnValues(CodeAttribute codeAttribute, List<String> columns, int level) {
		CodeAttributeDefinition definition = codeAttribute.getDefinition();
		CodeList codeList = definition.getList();
		List<CodeListLevel> hierarchy = codeList.getHierarchy();
		for(int i = level+1; i<hierarchy.size(); i++){
			CodeListLevel codeListLevel = hierarchy.get(i);
			String name = codeListLevel.getName();
			columns.add(name);
			columns.add("");
		}
	}

	private String getListName(CodeAttribute codeAttribute) {
		CodeAttributeDefinition definition = codeAttribute.getDefinition();
		CodeList list = definition.getList();
		return list.getName();
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

	private String getColumnName(CodeAttribute codeAttribute) {
		CodeAttributeDefinition attributeDefinition = codeAttribute.getDefinition();
		CodeList codeList = attributeDefinition.getList();

		CodeAttributeDefinition parentDefinition = attributeDefinition.getParentCodeAttributeDefinition();
		int level = 0;
		while (parentDefinition != null) {
			level++;
			parentDefinition = parentDefinition.getParentCodeAttributeDefinition();
		}

		List<CodeListLevel> hierarchy = codeList.getHierarchy();
		CodeListLevel codeListLevel = hierarchy.get(level);
		return codeListLevel.getName();
	}

}
