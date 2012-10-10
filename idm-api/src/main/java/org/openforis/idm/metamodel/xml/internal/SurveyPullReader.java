package org.openforis.idm.metamodel.xml.internal;


import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;
import org.openforis.idm.metamodel.xml.XmlParseException;

public class SurveyPullReader extends IdmlPullReader {

	private Survey survey;
	 
	public SurveyPullReader(SurveyIdmlBinder binder) {
		super("survey");
		
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
		String lastId = getAttribute("lastId", true);
		Boolean published = getBooleanAttribute("published", false);
		SurveyContext surveyContext = getSurveyBinder().getSurveyContext(); 
		this.survey = surveyContext.createSurvey();
		survey.setLastId(Integer.valueOf(lastId));
		survey.setPublished(published == null ? false : published);
	}

	// TAG READERS
	
	private class ProjectPR extends LanguageSpecificTextPR {
		public ProjectPR() {
			super("project");
		}
		
		@Override
		protected void processText(LanguageSpecificText lst) {
			survey.addProjectName(lst);
		}
	}
	
	private class UriPR extends TextPullReader {
		public UriPR() {
			super("uri", 1);
		}
		
		@Override
		protected void processText(String text) {
			survey.setUri(text);
		}
	}
	
	private class CyclePR extends TextPullReader {
		public CyclePR() {
			super("cycle", 1);
		}
		
		@Override
		protected void processText(String text) {
			survey.setCycle(text);
		}
	}
	
	private class DescriptionPR extends LanguageSpecificTextPR {
		public DescriptionPR() {
			super("description");
		}
		
		@Override
		protected void processText(LanguageSpecificText lst) {
			survey.addDescription(lst);
		}
	}
	
	private class LanguagePR extends TextPullReader {
		public LanguagePR() {
			super("language");
		}

		@Override
		protected void processText(String text) {
			survey.addLanguage(text);
		}
	}
}
