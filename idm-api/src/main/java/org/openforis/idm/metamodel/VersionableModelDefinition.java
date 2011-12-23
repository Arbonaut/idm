package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
public abstract class VersionableModelDefinition extends ModelDefinition {

	@XmlTransient
	private ModelVersion sinceVersion;

	@XmlTransient
	private ModelVersion deprecatedVersion;
	
	public String getSince() {
		return sinceVersion == null ? null : sinceVersion.getName();
	}
	
	@XmlAttribute(name = "since")
	void setSince(String name) {
		this.sinceVersion = findVersion(name);
	};

	public String getDeprecated() {
		return deprecatedVersion == null ? null : deprecatedVersion.getName();
	}
	
	@XmlAttribute(name = "deprecated")
	void setDeprecated(String name) {
		this.deprecatedVersion = findVersion(name);
	};


	public ModelVersion getSinceVersion() {
		return this.sinceVersion;
	}

	void setSinceVersion(ModelVersion since) {
		this.sinceVersion = since;
	}

	public ModelVersion getDeprecatedVersion() {
		return this.deprecatedVersion;
	}

	void setDeprecatedVersion(ModelVersion deprecated) {
		this.deprecatedVersion = deprecated;
	}

	private ModelVersion findVersion(String name) {
		if ( name == null ) {
			return null;
		} else {
			Survey survey = getSurvey();
			if ( survey == null ) {
				throw new IllegalStateException("Survey not set!");
			} 
			ModelVersion v = survey.getVersion(name);
			if ( v == null ) {
				throw new IllegalArgumentException("Undefined version '"+name+"' in "+toString());
			} 
			return v;
		}
	}
	
}
