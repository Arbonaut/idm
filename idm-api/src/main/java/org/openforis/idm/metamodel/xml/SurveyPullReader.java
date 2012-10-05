package org.openforis.idm.metamodel.xml;


import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.xmlpull.v1.XmlPullParser;

public class SurveyPullReader extends IdmlPullReader {

	private Survey survey;
	private SurveyContext surveyContext;
	 
	protected SurveyPullReader(SurveyContext surveyContext) {
		super("survey");
		this.surveyContext = surveyContext;
		setChildPullReaders(
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
	public boolean onStartTag(XmlPullParser parser) throws XmlParseException {
		// TODO update test IDML so that ids are unique within file and that lastId is correct
		String lastId = getAttribute(parser, "lastId", true);
		this.survey = new Survey(surveyContext, Integer.valueOf(lastId));
		return false;
	}

	// TAG READERS
	
	private class ProjectPR extends LanguageSpecificTextPullReader {
		public ProjectPR() {
			super("project");
		}
		
		@Override
		public void processText(LanguageSpecificText lst) {
			survey.addProjectName(lst);
		}
	}
	
	private class UriPR extends TextPullReader {
		public UriPR() {
			super("uri", 1);
		}
		
		@Override
		public void processText(String text) {
			survey.setUri(text);
		}
	}
	
	private class CyclePR extends TextPullReader {
		public CyclePR() {
			super("cycle", 1);
		}
		
		@Override
		public void processText(String text) {
			survey.setCycle(text);
		}
	}
	
	private class DescriptionPR extends LanguageSpecificTextPullReader {
		public DescriptionPR() {
			super("description");
		}
		
		@Override
		public void processText(LanguageSpecificText lst) {
			survey.addDescription(lst);
		}
	}
}
