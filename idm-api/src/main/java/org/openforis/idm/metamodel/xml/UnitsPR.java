package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.Unit;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
public class UnitsPR extends IdmlPullReader {

	public UnitsPR() {
		super("units", 1);
		setChildPullReaders(new UnitPR());
	}
	
	private class UnitPR extends IdmlPullReader {

		private Unit unit;
		
		public UnitPR() {
			super("unit");
			setChildPullReaders(new LabelPR(), new AbbreviationPR());
		}

		@Override
		protected boolean onStartTag(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
			int id = getIntegerAttribute(parser, "id", true);
			String name = getAttribute(parser, "name", true);
			String dimension = getAttribute(parser, "dimension", true);
			Float conversionFactor = getFloatAttribute(parser, "conversionFactor", false);
			Survey survey = getSurvey();
			this.unit = survey.createUnit(id);
			unit.setName(name);
			unit.setDimension(dimension);
			unit.setConversionFactor(conversionFactor);
			return false;
		}
		
		private class LabelPR extends LanguageSpecificTextPullReader {
			public LabelPR() {
				super("label");
			}
			
			@Override
			public void processText(LanguageSpecificText lst) {
				unit.addLabel(lst);
			}
		}

		private class AbbreviationPR extends LanguageSpecificTextPullReader {
			public AbbreviationPR() {
				super("abbreviation");
			}
			
			@Override
			public void processText(LanguageSpecificText lst) {
				unit.addAbbreviation(lst);
			}
		}
		
		@Override
		protected void onEndTag(XmlPullParser parser) throws XmlParseException {
			Survey survey = getSurvey();
			survey.addUnit(unit);
		}
	}
}