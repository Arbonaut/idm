package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Survey;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author G. Miceli
 */
public class VersioningPR extends IdmlPullReader {
	
	public VersioningPR() {
		super("versioning", 1);
		setChildPullReaders(new VersionPR());
	}

	private class VersionPR extends IdmlPullReader {

		private ModelVersion version;
		
		public VersionPR() {
			super("version");
			setChildPullReaders(new LabelPR(), new DescriptionPR(), new DatePR());
		}
		
		@Override
		protected boolean onStartTag(XmlPullParser parser) throws XmlParseException {
			int id = getIntegerAttribute(parser, "id", true);
			String name = getAttribute(parser, "name", false);
			Survey survey = getSurvey();
			version = survey.createModelVersion(id);
			version.setName(name);
			return false;
		}

		private class LabelPR extends LanguageSpecificTextPullReader {
			public LabelPR() {
				super("label");
			}
			
			@Override
			public void processText(LanguageSpecificText lst) {
				version.addLabel(lst);
			}
		}

		private class DescriptionPR extends LanguageSpecificTextPullReader {
			public DescriptionPR() {
				super("description");
			}
			
			@Override
			public void processText(LanguageSpecificText lst) {
				version.addDescription(lst);
			}
		}

		private class DatePR extends TextPullReader {
			public DatePR() {
				super("date", 1);
			}
			
			@Override
			public void processText(String text) {
				version.setDate(text);
			}
		}
	
		@Override
		protected void onEndTag(XmlPullParser parser) throws XmlParseException {
			Survey survey = version.getSurvey();
			survey.addVersion(version);
			this.version = null;
		}
	}
}