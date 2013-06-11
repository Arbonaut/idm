package org.openforis.idm.metamodel.xml.internal.unmarshal;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

/**
 * @author G. Miceli
 */
public class SurveyUnmarshaller extends IdmlPullReader {

	private Survey survey;
	 
	public SurveyUnmarshaller(SurveyIdmlBinder binder) {
		super(SURVEY);
		
		if ( binder == null ) {
			throw new NullPointerException("binder");
		}
		
		addChildPullReaders(
			new ProjectPR(), 
			new UriPR(), 
			new CyclePR(),
			new DescriptionPR(),
			new LanguagePR(),
			new ApplicationOptionsPR(),
			new VersioningPR(), 
			createCodeListReader(),
			new UnitsPR(),
			new SpatialReferenceSystemsPR(),
			new SchemaPR());

		setSurveyBinder(binder);
	}

	protected CodeListsPR createCodeListReader() {
		return new CodeListsPR();
	}
	
	@Override
	public Survey getSurvey() {
		return survey;
	}

	@Override
	public void onStartTag() throws XmlParseException {
		String lastId = getAttribute(LAST_ID, true);
		Boolean published = getBooleanAttribute(PUBLISHED, false);
		SurveyIdmlBinder surveyBinder = getSurveyBinder();
		SurveyContext surveyContext = surveyBinder.getSurveyContext(); 
		this.survey = surveyContext.createSurvey();
		survey.setLastId(Integer.valueOf(lastId));
		survey.setPublished(published == null ? false : published);
		readNamepaceDeclarations();
	}

	protected void readNamepaceDeclarations() throws XmlParseException {
		XmlPullParser pp = getParser();
		try {
			int nsCount = pp.getNamespaceCount(1);
			for (int i = 0; i < nsCount; i++) {
				String prefix = pp.getNamespacePrefix(i);
				if ( prefix != null ) {
					String uri = pp.getNamespaceUri(i);
					survey.addCustomNamespace(uri, prefix);
				}
			}
		} catch (XmlPullParserException e) {
			throw new XmlParseException(pp, "Failed to read namespace declarations", e);
		}
	}

	// TAG READERS
	
	private class ProjectPR extends LanguageSpecificTextPR {
		public ProjectPR() {
			super(PROJECT);
		}
		
		@Override
		protected void processText(LanguageSpecificText lst) {
			survey.addProjectName(lst);
		}
	}
	
	private class UriPR extends TextPullReader {

		public UriPR() {
			super(URI, 1);
		}
		
		@Override
		protected void processText(String text) {
			survey.setUri(text);
		}
	}
	
	private class CyclePR extends TextPullReader {

		public CyclePR() {
			super(CYCLE, 1);
		}
		
		@Override
		protected void processText(String text) {
			survey.setCycle(text);
		}
	}
	
	private class DescriptionPR extends LanguageSpecificTextPR {

		public DescriptionPR() {
			super(DESCRIPTION);
		}
		
		@Override
		protected void processText(LanguageSpecificText lst) {
			survey.addDescription(lst);
		}
	}
	
	private class LanguagePR extends TextPullReader {

		public LanguagePR() {
			super(LANGUAGE);
		}

		@Override
		protected void processText(String text) {
			survey.addLanguage(text);
		}
	}
}
