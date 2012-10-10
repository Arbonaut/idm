package org.openforis.idm.metamodel.xml.internal.marshal;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;
import org.openforis.idm.metamodel.xml.XmlParseException;
import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

/**
 * @author G. Miceli
 */
public class SurveyPullReader extends IdmlPullReader {

	private Survey survey;
	 
	public SurveyPullReader(SurveyIdmlBinder binder) {
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
			new CodeListsPR(),
			new UnitsPR(),
			new SpatialReferenceSystemsPR(),
			new SchemaPR());

		setSurveyBinder(binder);
	}
	
	@Override
	public Survey getSurvey() {
		return survey;
	}

	@Override
	public void onStartTag() throws XmlParseException {
		String lastId = getAttribute(LAST_ID, true);
		Boolean published = getBooleanAttribute(PUBLISHED, false);
		SurveyContext surveyContext = getSurveyBinder().getSurveyContext(); 
		this.survey = surveyContext.createSurvey();
		survey.setLastId(Integer.valueOf(lastId));
		survey.setPublished(published == null ? false : published);
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
