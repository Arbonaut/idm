package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.NodeLabel;
import org.openforis.idm.metamodel.Prompt;
import org.openforis.idm.metamodel.Schema;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
abstract class NodeDefinitionPR extends IdmlPullReader {
	protected NodeDefinition defn;
	protected NodeDefinition parentDefn;
	protected Schema schema;
	
	public NodeDefinitionPR(String tagName) {
		super(tagName);
		setUnordered(true);
	}
	
	
	@Override
	protected boolean onStartTag(XmlPullParser parser)
			throws XmlParseException, XmlPullParserException,
			IOException {				
		schema = getSurvey().getSchema(); 
		int id = getIntegerAttribute(parser, "id", true);
		this.defn = createDefinition(id);
		
		String name = getAttribute(parser, "name", true);
		String since = getAttribute(parser, "since", false);
		String deprecated = getAttribute(parser, "deprecated", false);
		Boolean required = getBooleanAttribute(parser, "required", false);
		String requiredIf = getAttribute(parser, "requiredIf", false);
		String relevant = getAttribute(parser, "relevant", false);
		Integer minCount = getIntegerAttribute(parser, "minCount", false);
		Boolean multiple = getBooleanAttribute(parser, "multiple", false);
		multiple = multiple==null ? false : multiple;
		Integer maxCount = getIntegerAttribute(parser, "maxCount", multiple && defn instanceof AttributeDefinition);
		// TODO parse "other" attributes (annotations)
		this.parentDefn = this.defn;
		defn.setMultiple(multiple);
		defn.setName(name);
		defn.setSinceVersionByName(since);
		defn.setDeprecatedVersionByName(deprecated);
		if ( minCount == null && required != null && required ) {
			defn.setMinCount(1);
		} else {
			defn.setMinCount(minCount);
		}
		defn.setMaxCount(maxCount);
		defn.setRequiredExpression(requiredIf);
		defn.setRelevantExpression(relevant);
		
		return false;
	}
	
	protected abstract NodeDefinition createDefinition(int id);
	
	@Override
	protected void onEndTag(XmlPullParser parser)
			throws XmlParseException {
		this.defn = parentDefn;
		this.parentDefn = defn.getParentDefinition();
	}
	
	protected class LabelPR extends LanguageSpecificTextPullReader {
		public LabelPR() {
			super("label");
		}
		
		@Override
		public void processText(String lang, String typeStr, String text) {
			// TODO throw Exception if typeStr is empty
			NodeLabel.Type type = typeStr == null ? NodeLabel.Type.INSTANCE : NodeLabel.Type.valueOf(typeStr.toUpperCase()); 
			NodeLabel label = new NodeLabel(type, lang, text);
			defn.addLabel(label);
		}
	}
	
	protected class PromptPR extends LanguageSpecificTextPullReader {
		public PromptPR() {
			super("prompt");
		}
		
		@Override
		public void processText(String lang, String typeStr, String text) {
			// TODO throw Exception if typeStr is empty
			Prompt.Type type = Prompt.Type.valueOf(typeStr.toUpperCase()); 
			Prompt p = new Prompt(type, lang, text);
			defn.addPrompt(p);
		}
	}

	protected class DescriptionPR extends LanguageSpecificTextPullReader {
		public DescriptionPR() {
			super("description");
		}
		
		@Override
		public void processText(LanguageSpecificText lst) {
			defn.addDescription(lst);
		}
	}
}