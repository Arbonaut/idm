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
import org.openforis.idm.metamodel.validation.DistanceCheck;
import org.openforis.idm.metamodel.validation.PatternCheck;
import org.openforis.idm.metamodel.validation.UniquenessCheck;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

abstract class AttributeDefinitionPR extends NodeDefinitionPR {

	public AttributeDefinitionPR(String tagName) {
		super(tagName);
		setChildPullReaders(
				new LabelPR(), 
				new DescriptionPR(),
				new DefaultPR(),
				new PrecisionPR(),
				new CompareCheckPR(),
				new UniquenessCheckPR(),
				new DistanceCheckPR(), 
				new PatternCheckPR()
		);
	}

	private class DefaultPR extends IdmlPullReader {
		private AttributeDefault attributeDefault;
		
		protected DefaultPR() {
			super("default");
		}

		@Override
		protected boolean onStartTag(XmlPullParser parser)
				throws XmlParseException, XmlPullParserException, IOException {
			this.attributeDefault = new AttributeDefault();
			attributeDefault.setCondition(getAttribute(parser, "if", false));
			attributeDefault.setExpression(getAttribute(parser, "expr", false));
			attributeDefault.setValue(getAttribute(parser, "value", false));
			return false;
		}
		
		protected void onEndTag(XmlPullParser parser) throws XmlParseException {
			((AttributeDefinition) defn).addAttributeDefault(attributeDefault);
		}
	}

	private class PrecisionPR extends IdmlPullReader {
		private Precision precision;
		
		protected PrecisionPR() {
			super("precision");
		}

		@Override
		protected boolean onStartTag(XmlPullParser parser)
				throws XmlParseException, XmlPullParserException, IOException {
			this.precision = new Precision();
			Boolean isDefault = getBooleanAttribute(parser, "value", false);
			Integer decimalDigits = getIntegerAttribute(parser, "decimalDigits", false);
			String unitName = getAttribute(parser, "unit", false);
			Unit unit = getSurvey().getUnit(unitName);
			
			precision.setDecimalDigits(decimalDigits);
			precision.setUnit(unit);
			precision.setDefaultPrecision(isDefault == null ? false : isDefault);
			return false;
		}
		
		protected void onEndTag(XmlPullParser parser) throws XmlParseException {
			((NumericAttributeDefinition) defn).addPrecisionDefinition(precision);
		}
	}

	protected abstract class CheckPR extends IdmlPullReader {
		protected Check<?> check;
		
		protected CheckPR(String tagName) {
			super(tagName);
			setChildPullReaders(new MessagesPR());
		}
		
		@Override
		protected boolean onStartTag(XmlPullParser parser)
				throws XmlParseException, XmlPullParserException,
				IOException {
			this.check = createCheck(parser);
			String flagStr = getAttribute(parser, "flag", true);
			// check that flag is value
			Check.Flag flag = Check.Flag.valueOf(flagStr.toUpperCase());
			String condition = getAttribute(parser, "if", false);
			check.setFlag(flag);
			check.setCondition(condition);
			return false;
		}
		
		protected abstract Check<?> createCheck(XmlPullParser parser);
		
		private class MessagesPR extends LanguageSpecificTextPullReader {
			public MessagesPR() {
				super("message");
			}
			@Override
			public void processText(LanguageSpecificText lst) {
				check.addMessage(lst);
			}
		}
		
		@Override
		protected void onEndTag(XmlPullParser parser)
				throws XmlParseException {
			((AttributeDefinition) defn).addCheck(check);
		}
	}
	
	private class CompareCheckPR extends CheckPR {

		protected CompareCheckPR() {
			super("compare");
		}
		
		@Override
		protected boolean onStartTag(XmlPullParser parser)
				throws XmlParseException, XmlPullParserException, IOException {
			super.onStartTag(parser);
			ComparisonCheck chk = (ComparisonCheck) check;
			chk.setEqualsExpression(getAttribute(parser, "eq", false));
			chk.setLessThanExpression(getAttribute(parser, "lt", false));
			chk.setLessThanOrEqualsExpression(getAttribute(parser, "lte", false));
			chk.setGreaterThanExpression(getAttribute(parser, "gt", false));
			chk.setGreaterThanOrEqualsExpression(getAttribute(parser, "gte", false));
			return false;
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
		protected boolean onStartTag(XmlPullParser parser)
				throws XmlParseException, XmlPullParserException, IOException {
			super.onStartTag(parser);
			DistanceCheck chk = (DistanceCheck) check;
			chk.setMinDistanceExpression(getAttribute(parser, "min", false));
			chk.setMaxDistanceExpression(getAttribute(parser, "max", false));
			chk.setSourcePointExpression(getAttribute(parser, "from", false));
			chk.setDestinationPointExpression(getAttribute(parser, "to", false));
			
			return false;
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
		protected boolean onStartTag(XmlPullParser parser)
				throws XmlParseException, XmlPullParserException, IOException {
			super.onStartTag(parser);
			UniquenessCheck chk = (UniquenessCheck) check;
			chk.setExpression(getAttribute(parser, "expr", true));
			return false;
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
		protected boolean onStartTag(XmlPullParser parser)
				throws XmlParseException, XmlPullParserException, IOException {
			super.onStartTag(parser);
			PatternCheck chk = (PatternCheck) check;
			chk.setRegularExpression(getAttribute(parser, "regex", true));
			return false;
		}

		@Override
		protected Check<?> createCheck(XmlPullParser parser) {
			return new PatternCheck();
		}
	}
}