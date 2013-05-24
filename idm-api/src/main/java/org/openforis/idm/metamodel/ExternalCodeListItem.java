/**
 * 
 */
package org.openforis.idm.metamodel;

/**
 * @author S. Ricci
 *
 */
public class ExternalCodeListItem extends CodeListItem {

	private static final long serialVersionUID = 1L;

	public ExternalCodeListItem(CodeList codeList, int id) {
		super(codeList, id);
	}
	
	@Override
	public CodeListItem getParentItem() {
		Survey survey = getSurvey();
		SurveyContext context = survey.getContext();
		ExternalCodeListProvider provider = context.getExternalCodeListProvider();
		ExternalCodeListItem parentItem = provider.getParentItem(this);
		return parentItem;
	}

}
