package org.openforis.idm.metamodel.xml;


import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;

public class SurveyPullReader extends IdmlPullReader {

	private Survey survey;
	private SurveyContext surveyContext;
	 
	protected SurveyPullReader(SurveyContext surveyContext) {
		super("survey");
		this.surveyContext = surveyContext;
		addChildPullReaders(
			new ProjectPR(), 
			new UriPR(), 
			new CyclePR(),
			new DescriptionPR(), 
			new ConfigurationPR(),
			new VersioningPR(), 
			new CodeListsPR(),
			new UnitsPR(),
			new SrsesPR(),
			new SchemaPR());
	}
	
	@Override
	public Survey getSurvey() {
		return survey;
	}

	// TODO wrap exceptions with own class
	@Override
	public boolean onStartTag() throws XmlParseException {
		// TODO update test IDML so that ids are unique within file and that lastId is correct
		String lastId = getAttribute("lastId", true);
		this.survey = surveyContext.createSurvey();
		survey.setLastId(Integer.valueOf(lastId));
		return false;
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
}
