package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.NodeLabel;
import org.openforis.idm.metamodel.Prompt;
import org.openforis.idm.metamodel.Schema;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
abstract class NodeDefinitionPR extends IdmlPullReader {
	private NodeDefinition definition;
	private EntityDefinition parentDefinition;
	private Schema schema;
	
	public NodeDefinitionPR(String tagName) {
		super(tagName);
		setUnordered(true);
		addChildPullReaders(
				new LabelPR(), 
				new DescriptionPR(),
				new PromptPR()
			);
	}
	
	protected NodeDefinition getDefinition() {
		return definition;
	}
	
	protected EntityDefinition getParentDefinition() {
		return parentDefinition;
	}

	public Schema getSchema() {
		return schema;
	}
	
	@Override
	protected final void onStartTag()
			throws XmlParseException, XmlPullParserException,
			IOException {				
		schema = getSurvey().getSchema(); 
		int id = getIntegerAttribute("id", true);
		this.definition = createDefinition(id);
		
		String name = getAttribute("name", true);
		String since = getAttribute("since", false);
		String deprecated = getAttribute("deprecated", false);
		Boolean required = getBooleanAttribute("required", false);
		String requiredIf = getAttribute("requiredIf", false);
		String relevant = getAttribute("relevant", false);
		Integer minCount = getIntegerAttribute("minCount", false);
		Boolean multiple = getBooleanAttribute("multiple", false);
		if ( parentDefinition == null ) {
			if ( multiple != null ) {
				throw new XmlParseException(getParser(), "attribute 'multiple' not allowed for root entity");
			}
			multiple = true;
		} else {
			multiple = multiple==null ? false : multiple;
		}
		// TODO maxCount should be required for multiple attributes
//		Integer maxCount = getIntegerAttribute("maxCount", multiple && defn instanceof AttributeDefinition);
		Integer maxCount = getIntegerAttribute("maxCount", false);
		// TODO parse "other" attributes (annotations)
		definition.setMultiple(multiple);
		definition.setName(name);
		definition.setSinceVersionByName(since);
		definition.setDeprecatedVersionByName(deprecated);
		if ( minCount == null && required != null && required ) {
			definition.setMinCount(1);
		} else {
			definition.setMinCount(minCount);
		}
		definition.setMaxCount(maxCount);
		definition.setRequiredExpression(requiredIf);
		definition.setRelevantExpression(relevant);
		
		onStartDefinition();
	}
	
	protected void onStartDefinition() throws XmlParseException, XmlPullParserException, IOException {
		// no-op
	}
	
	@Override
	protected void handleAnnotation(QName qName, String value) {
		definition.setAnnotation(qName, value);
	}
	
	@Override
	protected void handleChildTag(XmlPullReader pr)
			throws XmlPullParserException, IOException, XmlParseException {
		
		if ( pr instanceof NodeDefinitionPR ) {
			NodeDefinitionPR npr = (NodeDefinitionPR) pr;
			EntityDefinition tmpParent = npr.parentDefinition;
			npr.parentDefinition = (EntityDefinition) this.definition;
			
			super.handleChildTag(pr);
			
			npr.parentDefinition = tmpParent;
		} else {
			super.handleChildTag(pr);
		}
	}
	
	protected abstract NodeDefinition createDefinition(int id);
	
	@Override
	protected void onEndTag()
			throws XmlParseException {
		EntityDefinition parentDefinition = getParentDefinition();
		NodeDefinition definition = getDefinition();
		if ( parentDefinition == null ) {
			Schema schema = getSchema();
			schema.addRootEntityDefinition((EntityDefinition) definition);
		} else {			
			parentDefinition.addChildDefinition(definition);
		}
	}
	protected class LabelPR extends LanguageSpecificTextPR {
		public LabelPR() {
			super("label");
		}
		
		@Override
		protected void processText(String lang, String typeStr, String text) throws XmlParseException {
			try { 
				NodeLabel.Type type = typeStr == null ? NodeLabel.Type.INSTANCE : NodeLabel.Type.valueOf(typeStr.toUpperCase()); 
				NodeLabel label = new NodeLabel(type, lang, text);
				definition.addLabel(label);
			} catch (IllegalArgumentException e) {
				throw new XmlParseException(getParser(), "invalid type "+typeStr);
			}
		}
	}
	
	protected class PromptPR extends LanguageSpecificTextPR {
		public PromptPR() {
			super("prompt", true);
		}
		
		@Override
		protected void processText(String lang, String typeStr, String text) throws XmlParseException {
			try {
				Prompt.Type type = Prompt.Type.valueOf(typeStr.toUpperCase()); 
				Prompt p = new Prompt(type, lang, text);
				definition.addPrompt(p);
			} catch (IllegalArgumentException e) {
				throw new XmlParseException(getParser(), "invalid type "+typeStr);
			}
		}
	}

	protected class DescriptionPR extends LanguageSpecificTextPR {
		public DescriptionPR() {
			super("description");
		}
		
		@Override
		protected void processText(LanguageSpecificText lst) {
			definition.addDescription(lst);
		}
	}
}