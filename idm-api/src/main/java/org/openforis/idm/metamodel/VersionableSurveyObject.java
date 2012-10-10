package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
public abstract class VersionableSurveyObject extends IdentifiableSurveyObject {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private ModelVersion sinceVersion;

	@XmlTransient
	private ModelVersion deprecatedVersion;

	protected VersionableSurveyObject(Survey survey, int id) {
		super(survey, id);
	}

	@XmlAttribute(name = "since")
	public void setSinceVersionByName(String name) {
		this.sinceVersion = name == null ? null : findVersion(name);
	}

	@XmlAttribute(name = "deprecated")
	public void setDeprecatedVersionByName(String name) {
		this.deprecatedVersion = name == null ? null : findVersion(name);
	}

	public ModelVersion getSinceVersion() {
		return this.sinceVersion;
	}

	public void setSinceVersion(ModelVersion since) {
		this.sinceVersion = since;
	}

	public ModelVersion getDeprecatedVersion() {
		return this.deprecatedVersion;
	}

	public void setDeprecatedVersion(ModelVersion deprecated) {
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
		VersionableSurveyObject other = (VersionableSurveyObject) obj;
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
