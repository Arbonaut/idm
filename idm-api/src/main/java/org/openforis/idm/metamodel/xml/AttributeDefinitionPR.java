package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.AttributeDefault;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.NumericAttributeDefinition;
import org.openforis.idm.metamodel.Precision;
import org.openforis.idm.metamodel.Unit;
import org.openforis.idm.metamodel.validation.Check;
import org.openforis.idm.metamodel.validation.ComparisonCheck;
import org.openforis.idm.metamodel.validation.CustomCheck;
import org.openforis.idm.metamodel.validation.DistanceCheck;
import org.openforis.idm.metamodel.validation.PatternCheck;
import org.openforis.idm.metamodel.validation.UniquenessCheck;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
abstract class AttributeDefinitionPR extends NodeDefinitionPR {
	
	public AttributeDefinitionPR(String tagName) {
		super(tagName);
		addChildPullReaders(
				new DefaultPR(),
				new PrecisionPR(),
				new CompareCheckPR(),
				new UniquenessCheckPR(),
				new DistanceCheckPR(), 
				new PatternCheckPR(),
				new CustomCheckPR()
		);
	}

	private class DefaultPR extends IdmlPullReader {
		private AttributeDefault attributeDefault;
		
		protected DefaultPR() {
			super("default");
		}

		@Override
		protected void onStartTag()
				throws XmlParseException, XmlPullParserException, IOException {
			super.onStartTag();
			this.attributeDefault = new AttributeDefault();
			attributeDefault.setCondition(getAttribute("if", false));
			attributeDefault.setExpression(getAttribute("expr", false));
			attributeDefault.setValue(getAttribute("value", false));
		}
		
		@Override
		protected void onEndTag() throws XmlParseException {
			((AttributeDefinition) getDefinition()).addAttributeDefault(attributeDefault);
		}
	}

	private class PrecisionPR extends IdmlPullReader {
		private Precision precision;
		
		protected PrecisionPR() {
			super("precision");
		}

		@Override
		protected void onStartTag()
				throws XmlParseException, XmlPullParserException, IOException {
			this.precision = new Precision();
			Boolean isDefault = getBooleanAttribute("value", false);
			Integer decimalDigits = getIntegerAttribute( "decimalDigits", false);
			String unitName = getAttribute("unit", false);
			Unit unit = getSurvey().getUnit(unitName);
			
			precision.setDecimalDigits(decimalDigits);
			precision.setUnit(unit);
			precision.setDefaultPrecision(isDefault == null ? false : isDefault);
		}
		
		@Override
		protected void onEndTag() throws XmlParseException {
			((NumericAttributeDefinition) getDefinition()).addPrecisionDefinition(precision);
		}
	}

	protected abstract class CheckPR extends IdmlPullReader {
		protected Check<?> check;
		
		protected CheckPR(String tagName) {
			super(tagName);
			addChildPullReaders(new MessagesPR());
		}
		
		@Override
		protected void onStartTag()
				throws XmlParseException, XmlPullParserException, IOException {
			XmlPullParser parser = getParser();
			this.check = createCheck(parser);
			String flagStr = getAttribute("flag", true);
			Check.Flag flag;
			try {
				flag = Check.Flag.valueOf(flagStr.toUpperCase());
			} catch ( IllegalArgumentException e ) {
				throw new XmlParseException(getParser(), "invalid flag "+flagStr);
			}
			String condition = getAttribute("if", false);
			check.setFlag(flag);
			check.setCondition(condition);
		}
		
		protected abstract Check<?> createCheck(XmlPullParser parser);
		
		private class MessagesPR extends LanguageSpecificTextPR {
			public MessagesPR() {
				super("message");
			}
			
			@Override
			protected void processText(LanguageSpecificText lst) {
				check.addMessage(lst);
			}
		}
		
		@Override
		protected void onEndTag() throws XmlParseException {
			((AttributeDefinition) getDefinition()).addCheck(check);
		}
	}
	
	private class CompareCheckPR extends CheckPR {

		protected CompareCheckPR() {
			super("compare");
		}
		
		@Override
		protected void onStartTag()
				throws XmlParseException, XmlPullParserException, IOException {
			super.onStartTag();
			ComparisonCheck chk = (ComparisonCheck) check;
			chk.setEqualsExpression(getAttribute("eq", false));
			chk.setLessThanExpression(getAttribute("lt", false));
			chk.setLessThanOrEqualsExpression(getAttribute("lte", false));
			chk.setGreaterThanExpression(getAttribute("gt", false));
			chk.setGreaterThanOrEqualsExpression(getAttribute("gte", false));
		}

		@Override
		protected Check<?> createCheck(XmlPullParser parser) {
			return new ComparisonCheck();
		}
	}
	
	private class DistanceCheckPR extends CheckPR {

		protected DistanceCheckPR() {
			super("distance");
		}
		
		@Override
		protected void onStartTag()
				throws XmlParseException, XmlPullParserException, IOException {
			super.onStartTag();
			DistanceCheck chk = (DistanceCheck) check;
			chk.setMinDistanceExpression(getAttribute("min", false));
			chk.setMaxDistanceExpression(getAttribute("max", false));
			chk.setSourcePointExpression(getAttribute("from", false));
			chk.setDestinationPointExpression(getAttribute("to", false));
		}

		@Override
		protected Check<?> createCheck(XmlPullParser parser) {
			return new DistanceCheck();
		}
	}
	
	private class UniquenessCheckPR extends CheckPR {

		protected UniquenessCheckPR() {
			super("unique");
		}
		
		@Override
		protected void onStartTag()
				throws XmlParseException, XmlPullParserException, IOException {
			super.onStartTag();
			UniquenessCheck chk = (UniquenessCheck) check;
			chk.setExpression(getAttribute("expr", true));
		}

		@Override
		protected Check<?> createCheck(XmlPullParser parser) {
			return new UniquenessCheck();
		}
	}
	
	private class PatternCheckPR extends CheckPR {

		protected PatternCheckPR() {
			super("pattern");
		}
		
		@Override
		protected void onStartTag()
				throws XmlParseException, XmlPullParserException, IOException {
			super.onStartTag();
			PatternCheck chk = (PatternCheck) check;
			chk.setRegularExpression(getAttribute("regex", true));
		}

		@Override
		protected Check<?> createCheck(XmlPullParser parser) {
			return new PatternCheck();
		}
	}
	
	private class CustomCheckPR extends CheckPR {

		protected CustomCheckPR() {
			super("check");
		}
		
		@Override
		protected void onStartTag()
				throws XmlParseException, XmlPullParserException, IOException {
			super.onStartTag();
			CustomCheck chk = (CustomCheck) check;
			chk.setExpression(getAttribute("expr", true));
		}

		@Override
		protected Check<?> createCheck(XmlPullParser parser) {
			return new CustomCheck();
		}
	}
}