package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.Unit;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
public class UnitsPR extends IdmlPullReader {

	public UnitsPR() {
		super("units", 1);
		addChildPullReaders(new UnitPR());
	}
	
	private class UnitPR extends IdmlPullReader {

		private Unit unit;
		
		public UnitPR() {
			super("unit");
			addChildPullReaders(new LabelPR(), new AbbreviationPR());
		}

		@Override
		protected boolean onStartTag() throws XmlParseException, XmlPullParserException, IOException {
			int id = getIntegerAttribute("id", true);
			String name = getAttribute("name", true);
			String dimension = getAttribute("dimension", true);
			Float conversionFactor = getFloatAttribute("conversionFactor", false);
			Survey survey = getSurvey();
			this.unit = survey.createUnit(id);
			unit.setName(name);
			unit.setDimension(dimension);
			unit.setConversionFactor(conversionFactor);
			return false;
		}
		
		private class LabelPR extends LanguageSpecificTextPR {
			public LabelPR() {
				super("label");
			}
			
			@Override
			protected void processText(LanguageSpecificText lst) {
				unit.addLabel(lst);
			}
		}

		private class AbbreviationPR extends LanguageSpecificTextPR {
			public AbbreviationPR() {
				super("abbreviation");
			}
			
			@Override
			protected void processText(LanguageSpecificText lst) {
				unit.addAbbreviation(lst);
			}
		}
		
		@Override
		protected void onEndTag() throws XmlParseException {
			Survey survey = getSurvey();
			survey.addUnit(unit);
		}
	}
}