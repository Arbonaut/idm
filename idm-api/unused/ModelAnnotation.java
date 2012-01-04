package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlTransient;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
public class ModelAnnotation {

	private String namespace;
	private String name;
	private String value;

	public ModelAnnotation(String namespace, String name, String value) {
		this.namespace = namespace;
		this.name = name;
		this.value = value;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
