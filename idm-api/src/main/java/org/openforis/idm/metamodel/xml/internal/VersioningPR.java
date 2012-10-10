package org.openforis.idm.metamodel.xml.internal;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.XmlParseException;

/**
 * @author G. Miceli
 */
class VersioningPR extends IdmlPullReader {
	
	public VersioningPR() {
		super("versioning", 1);
		addChildPullReaders(new VersionPR());
	}

	private class VersionPR extends IdmlPullReader {

		private ModelVersion version;
		
		public VersionPR() {
			super("version");
			addChildPullReaders(new LabelPR(), new DescriptionPR(), new DatePR());
		}
		
		@Override
		protected void onStartTag() throws XmlParseException {
			int id = getIntegerAttribute("id", true);
			String name = getAttribute("name", false);
			Survey survey = getSurvey();
			version = survey.createModelVersion(id);
			version.setName(name);
		}

		private class LabelPR extends LanguageSpecificTextPR {
			public LabelPR() {
				super("label");
			}
			
			@Override
			protected void processText(LanguageSpecificText lst) {
				version.addLabel(lst);
			}
		}

		private class DescriptionPR extends LanguageSpecificTextPR {
			public DescriptionPR() {
				super("description");
			}
			
			@Override
			protected void processText(LanguageSpecificText lst) {
				version.addDescription(lst);
			}
		}

		private class DatePR extends TextPullReader {
			public DatePR() {
				super("date", 1);
			}
			
			@Override
			protected void processText(String text) {
				version.setDate(text);
			}
		}
	
		@Override
		protected void onEndTag() throws XmlParseException {
			Survey survey = version.getSurvey();
			survey.addVersion(version);
			this.version = null;
		}
	}
}