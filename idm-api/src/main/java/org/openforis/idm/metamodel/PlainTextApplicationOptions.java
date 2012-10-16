package org.openforis.idm.metamodel;


/**
 * @author G. Miceli
 */
public class PlainTextApplicationOptions implements ApplicationOptions {
	private String namespaceUri;
	private String body;
	
	public PlainTextApplicationOptions() {
	}

	public String getNamespaceUri() {
		return namespaceUri;
	}

	public void setNamespaceUri(String namespaceUri) {
		this.namespaceUri = namespaceUri;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
