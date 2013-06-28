package org.openforis.idm.metamodel.xml.internal.unmarshal;

import java.io.IOException;

import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.CodeListService;
import org.openforis.idm.metamodel.PersistedCodeListItem;
import org.openforis.idm.metamodel.xml.CodeListImporter;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
public class CodeListItemPersisterPR extends CodeListItemPR {
	
	private boolean itemPersisted;
	private int lastChildSortOrder;

	CodeListItemPersisterPR() {
		this(null);
	}
	
	CodeListItemPersisterPR(CodeListItem parentItem) {
		super(parentItem);
		itemPersisted = false;
		lastChildSortOrder = 0;
	}

	@Override
	protected void handleChildTag(XmlPullReader childTagReader)
			throws XmlPullParserException, IOException, XmlParseException {
		if ( childTagReader instanceof CodeListItemPersisterPR && 
			! itemPersisted ) {
			this.lastChildSortOrder = 0;
			persistItem(); //avoid NPE referencing parentId
		}
		super.handleChildTag(childTagReader);
	}
	
	@Override
	protected CodeListItemPR createChildItemPR(
			CodeListItem parentItem) {
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
	
	public int nextChildSortOrder() {
		return ++lastChildSortOrder;
	}

	protected void persistItem() {
		CodeListImporter binder = getBinder();
		PersistedCodeListItem persistedItem = (PersistedCodeListItem) item;
		persistedItem.setSystemId(binder.nextItemId());
		persistedItem.setSortOrder(calculateSortOrder());
		if ( parentItem != null ) {
			int parentId = ((PersistedCodeListItem) parentItem).getSystemId();
			persistedItem.setParentId(parentId);
		}
		binder.persistItem(persistedItem);
		itemPersisted = true;
	}
	
	protected int calculateSortOrder() {
		XmlPullReader parentReader = getParentReader();
		if ( parentReader instanceof CodeListItemsPersisterPR ) {
			return ((CodeListItemsPersisterPR) parentReader).nextChildSortOrder();
		} else if ( parentReader instanceof CodeListItemPersisterPR ) {
			return ((CodeListItemPersisterPR) parentReader).nextChildSortOrder();
		} else {
			throw new IllegalStateException("Unexpected parent reader type: " + parentReader.getClass().getName());
		}
	}
	
	protected CodeListService getCodeListService() {
		CodeListImporter binder = getBinder();
		CodeListService service = binder.getService();
		return service;
	}

	private CodeListImporter getBinder() {
		XmlPullReader parent = getParentReader();
		while ( parent != null ) {
			if ( parent instanceof SurveyCodeListImporterPR ) {
				return ((SurveyCodeListImporterPR) parent).getBinder();
			}
			parent = parent.getParentReader();
		}
		return null;
	}
	
}
