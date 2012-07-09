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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deprecatedVersion == null) ? 0 : deprecatedVersion.hashCode());
		result = prime * result + ((sinceVersion == null) ? 0 : sinceVersion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Versionable other = (Versionable) obj;
		if (deprecatedVersion == null) {
			if (other.deprecatedVersion != null)
				return false;
		} else if (!deprecatedVersion.equals(other.deprecatedVersion))
			return false;
		if (sinceVersion == null) {
			if (other.sinceVersion != null)
				return false;
		} else if (!sinceVersion.equals(other.sinceVersion))
			return false;
		return true;
	}
	
}
