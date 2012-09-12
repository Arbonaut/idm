package org.openforis.idm.metamodel;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Transient;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */
@Transient
public abstract class Versionable {

	@Transient
	private ModelVersion sinceVersion;

	@Transient
	private ModelVersion deprecatedVersion;

	private String sinceVersionName;

	private String deprecatedVersionName;
	
	public abstract Survey getSurvey();
	
	@Attribute(name = "since", required = false)
	public String getSinceVersionName() {
		return sinceVersionName;
	}
	
	@Attribute(name = "since", required = false)
	public void setSinceVersionName(String name) {
		this.sinceVersionName = name;
		this.sinceVersion = null;
	}

	@Attribute(name = "deprecated", required = false)
	public String getDeprecatedVersionName() {
		return deprecatedVersionName;
	}
	
	@Attribute(name = "deprecated", required = false)
	public void setDeprecatedVersionName(String name) {
		this.deprecatedVersionName = name;
		this.deprecatedVersion = null;
	}

	public ModelVersion getSinceVersion() {
		if ( sinceVersion == null ) {
			this.sinceVersion = findVersion(sinceVersionName);
		}
		return this.sinceVersion;
	}

	public void setSinceVersion(ModelVersion since) {
		this.sinceVersionName = since == null ? null : since.getName();
		this.sinceVersion = since;
	}

	public ModelVersion getDeprecatedVersion() {
		if ( deprecatedVersion == null ) {
			this.deprecatedVersion = findVersion(deprecatedVersionName);
		}
		return this.deprecatedVersion;
	}

	public void setDeprecatedVersion(ModelVersion deprecated) {
		this.deprecatedVersionName = deprecated == null ? null : deprecated.getName();
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
