package org.openforis.idm.metamodel.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.metamodel.CodeListService;
import org.openforis.idm.metamodel.PersistedCodeListItem;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyCodeListPersisterContext;
import org.openforis.idm.metamodel.xml.internal.unmarshal.SurveyCodeListPersister;

/**
 * Load code lists inside a XML file and store them into the database
 * using a ExternalCodeListPersister
 * 
 * @author S. Ricci
 */
public class SurveyCodeListPersisterBinder {
	
	private static final String UTF8_ENCODING = "UTF-8";

	private static final int BATCH_SIZE = 100;
	
	private SurveyCodeListPersisterContext context;
	private List<PersistedCodeListItem> itemsToPersistBuffer;
	private int nextItemId;
	
	public SurveyCodeListPersisterBinder(SurveyCodeListPersisterContext context, int nextItemSystemId) {
		this.context = context;
		this.nextItemId = nextItemSystemId;
		this.itemsToPersistBuffer = new ArrayList<PersistedCodeListItem>();
	}

	public void exportFromXMLAndStore(Survey survey, InputStream is) throws IdmlParseException {
		try {
			SurveyCodeListPersister persister = new SurveyCodeListPersister(this, survey);
			persister.parse(is, UTF8_ENCODING);
			flushItemsToPersistBuffer();
		} catch (XmlParseException e) {
			throw new IdmlParseException(e);
		} catch (IOException e) {
			throw new IdmlParseException(e);
		}
	}
	
	private void flushItemsToPersistBuffer() {
		CodeListService service = context.getCodeListService();
		service.save(itemsToPersistBuffer);
		itemsToPersistBuffer.clear();
	}

	public void persistItem(PersistedCodeListItem item)  {
		itemsToPersistBuffer.add(item);
		if ( itemsToPersistBuffer.size() > BATCH_SIZE) {
			flushItemsToPersistBuffer();
		}
	}
	
	public SurveyCodeListPersisterContext getContext() {
		return context;
	}

	public int nextItemId() {
		return nextItemId++;
	}
}
