package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
public abstract class Versionable {

	@XmlTransient
	private ModelVersion sinceVersion;

	@XmlTransient
	private ModelVersion deprecatedVersion;
	
	public abstract Survey getSurvey();
	
	public String getSinceVersionName() {
		return sinceVersion == null ? null : sinceVersion.getName();
	}
	
	@XmlAttribute(name = "since")
	protected void setSinceVersionName(String name) {
		this.sinceVersion = findVersion(name);
	}

	public String getDeprecatedVersionName() {
		return deprecatedVersion == null ? null : deprecatedVersion.getName();
	}
	
	@XmlAttribute(name = "deprecated")
	protected void setDeprecatedVersionName(String name) {
		this.deprecatedVersion = findVersion(name);
	}


	public ModelVersion getSinceVersion() {
		return this.sinceVersion;
	}

	protected void setSinceVersion(ModelVersion since) {
		this.sinceVersion = since;
	}

	public ModelVersion getDeprecatedVersion() {
		return this.deprecatedVersion;
	}

	protected void setDeprecatedVersion(ModelVersion deprecated) {
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
