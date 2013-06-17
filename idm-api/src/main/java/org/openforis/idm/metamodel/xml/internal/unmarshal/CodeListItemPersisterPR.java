package org.openforis.idm.metamodel.xml.internal.unmarshal;

import java.io.IOException;

import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.CodeListItemPersister;
import org.openforis.idm.metamodel.PersistedCodeListItem;
import org.openforis.idm.metamodel.SurveyCodeListPersisterContext;
import org.openforis.idm.metamodel.xml.SurveyCodeListPersisterBinder;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
public class CodeListItemPersisterPR extends CodeListItemPR {
	
	private boolean itemPersisted;

	CodeListItemPersisterPR() {
		itemPersisted = false;
	}
	
	CodeListItemPersisterPR(CodeListItem parentItem) {
		super(parentItem);
	}

	@Override
	protected CodeListItemPR createChildItemPR(
			CodeListItem parentItem) {
		if ( ! itemPersisted ) {
			persistItem(); //avoid NPE referencing parentId
		}
		return new CodeListItemPersisterPR(parentItem);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException,
			XmlPullParserException, IOException {
		itemPersisted = false;
		super.onStartTag();
	}

	@Override
	protected void onEndTag() throws XmlParseException {
		if ( ! itemPersisted ) {
			persistItem();
		}
	}

	@Override
	protected void createItem(int id) {
		item = new PersistedCodeListItem(getCodeList(), id);
	}
	
	protected void persistItem() {
		CodeListItemPersister persister = getPersister();
		PersistedCodeListItem persistedItem = (PersistedCodeListItem) item;
		if ( parentItem != null ) {
			int parentId = ((PersistedCodeListItem) parentItem).getSystemId();
			persistedItem.setParentId(parentId);
		}
		persister.save(persistedItem);
		itemPersisted = true;
	}
	
	protected CodeListItemPersister getPersister() {
		SurveyCodeListPersisterBinder binder = getBinder();
		SurveyCodeListPersisterContext context = binder.getContext();
		CodeListItemPersister persister = context.getCodeListPersister();
		return persister;
	}

	private SurveyCodeListPersisterBinder getBinder() {
		XmlPullReader parent = getParentReader();
		while ( parent != null ) {
			if ( parent instanceof SurveyCodeListPersister ) {
				return ((SurveyCodeListPersister) parent).getBinder();
			}
			parent = parent.getParentReader();
		}
		return null;
	}
	
}
