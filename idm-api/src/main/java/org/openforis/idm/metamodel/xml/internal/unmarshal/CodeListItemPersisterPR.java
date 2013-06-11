package org.openforis.idm.metamodel.xml.internal.unmarshal;

import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.ExternalCodeListItem;
import org.openforis.idm.metamodel.ExternalCodeListPersister;
import org.openforis.idm.metamodel.SurveyCodeListPersisterContext;
import org.openforis.idm.metamodel.xml.SurveyCodeListPersisterBinder;
import org.openforis.idm.metamodel.xml.XmlParseException;

/**
 * 
 * @author S. Ricci
 *
 */
public class CodeListItemPersisterPR extends CodeListItemPR {
	
	CodeListItemPersisterPR() {
		super();
	}
	
	CodeListItemPersisterPR(CodeListItem parentItem) {
		super(parentItem);
	}

	@Override
	protected CodeListItemPR createChildItemPR(
			CodeListItem parentItem) {
		return new CodeListItemPersisterPR(parentItem);
	}
	
	@Override
	protected void createItem(int id) {
		item = new ExternalCodeListItem(getCodeList(), id);
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		ExternalCodeListPersister persister = getPersister();
		if ( parentItem != null ) {
			int parentId = ((ExternalCodeListItem) parentItem).getSystemId();
			((ExternalCodeListItem) item).setParentId(parentId);
		}
		persister.save((ExternalCodeListItem) item);
	}

	protected ExternalCodeListPersister getPersister() {
		SurveyCodeListPersisterBinder binder = getBinder();
		SurveyCodeListPersisterContext context = binder.getContext();
		ExternalCodeListPersister persister = context.getExternalCodeListPersister();
		return persister;
	}

	private SurveyCodeListPersisterBinder getBinder() {
		XmlPullReader parent = getParentReader();
		while ( parent != null ) {
			if ( parent instanceof SurveyCodeListPersister ) {
				return ((SurveyCodeListPersister) parent).getBinder();
			}
			parent = getParentReader();
		}
		return null;
	}
	
}
