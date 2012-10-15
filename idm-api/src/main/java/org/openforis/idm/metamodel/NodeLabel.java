package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class NodeLabel extends TypedLanguageSpecificText<NodeLabel.Type> {

	public NodeLabel(Type type, String language, String text) {
		super(type, language, text);
	}

	private static final long serialVersionUID = 1L;

	public enum Type {
		HEADING, INSTANCE, NUMBER;
	}

}