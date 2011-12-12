/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlTransient;

import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Versionable;

/**
 * @author M. Togna
 * 
 */
public abstract class AbstractVersionable implements Versionable {

	@XmlTransient
	private ModelVersion since;

	@XmlTransient
	private ModelVersion deprecated;

	public ModelVersion getSince() {
		return since;
	}

	void setSince(ModelVersion since) {
		this.since = since;
	}

	public ModelVersion getDeprecated() {
		return deprecated;
	}

	void setDeprecated(ModelVersion deprecated) {
		this.deprecated = deprecated;
	}

}
