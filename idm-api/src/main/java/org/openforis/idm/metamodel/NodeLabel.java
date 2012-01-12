package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.xml.internal.NodeLabelTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class NodeLabel extends LanguageSpecificText {

	private static final long serialVersionUID = 1L;

	public enum Type {
		HEADING, INSTANCE, NUMBER;
	}

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(NodeLabelTypeAdapter.class)
	private NodeLabel.Type type;

	protected NodeLabel() {
	}

	public NodeLabel(Type type, String language, String text) {
		super(language, text);
		this.type = type;
	}

	public NodeLabel.Type getType() {
		return this.type;
	}
}