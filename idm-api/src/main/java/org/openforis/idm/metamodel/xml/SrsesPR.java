package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.SpatialReferenceSystem;
import org.openforis.idm.metamodel.Survey;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author G. Miceli
 */
public class SrsesPR extends IdmlPullReader {
	
	public SrsesPR() {
		super("spatialReferenceSystems", 1);
		setChildPullReaders(new SrsPR());
	}

	private class SrsPR extends IdmlPullReader {

		private SpatialReferenceSystem srs;
		
		public SrsPR() {
			super("spatialReferenceSystem");
			setChildPullReaders(new LabelPR(), new DescriptionPR(), new WktPR());
		}
		
		@Override
		protected boolean onStartTag(XmlPullParser parser) throws XmlParseException {
			String id = getAttribute(parser, "srid", true);
			this.srs = new SpatialReferenceSystem();
			srs.setId(id);
			return false;
		}

		private class LabelPR extends LanguageSpecificTextPullReader {
			public LabelPR() {
				super("label");
			}
			
			@Override
			public void processText(LanguageSpecificText lst) {
				srs.addLabel(lst);
			}
		}

		private class DescriptionPR extends LanguageSpecificTextPullReader {
			public DescriptionPR() {
				super("description");
			}
			
			@Override
			public void processText(LanguageSpecificText lst) {
				srs.addDescription(lst);
			}
		}

		private class WktPR extends TextPullReader {
			public WktPR() {
				super("wkt", 1);
				setTrimWhitespace(false);
			}

			@Override
			public void processText(String text) {
				srs.setWellKnownText(text);
			}
		}
	
		@Override
		protected void onEndTag(XmlPullParser parser) throws XmlParseException {
			Survey survey = getSurvey();
			survey.addSpatialReferenceSystem(srs);
			this.srs = null;
		}
	}
}