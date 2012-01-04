package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class NodeDefinitionLabel extends LanguageSpecificText {

	private static final long serialVersionUID = 1L;

	public enum Type {
		HEADING, INSTANCE, NUMBER;
	}

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = NodeDefinitionLabel.TypeAdapter.class)
	private NodeDefinitionLabel.Type type;

	protected NodeDefinitionLabel() {
	}

	public NodeDefinitionLabel(NodeDefinitionLabel.Type type, String language, String text) {
		super(language, text);
		this.type = type;
	}

	public NodeDefinitionLabel.Type getType() {
		return this.type;
	}

	private static class TypeAdapter extends XmlAdapter<String, NodeDefinitionLabel.Type> {
		@Override
		public NodeDefinitionLabel.Type unmarshal(String v) throws Exception {
			return v == null ? null : Type.valueOf(v.toUpperCase());
		}

		@Override
		public String marshal(NodeDefinitionLabel.Type v) throws Exception {
			return v == null ? null : v.toString().toLowerCase();
		}
	}
}