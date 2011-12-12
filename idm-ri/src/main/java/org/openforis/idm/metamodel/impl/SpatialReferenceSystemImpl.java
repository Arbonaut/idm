/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.SpatialReferenceSystem;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "wellKnownText", "labels", "descriptions" })
@XmlRootElement(name = "spatialReferenceSystem")
public class SpatialReferenceSystemImpl implements SpatialReferenceSystem {

	@XmlAttribute(name = "srid")
	private String id;

	@XmlElement(name = "label", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> labels;

	@XmlElement(name = "description", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(name = "wkt")
	private String wellKnownText;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public List<LanguageSpecificText> getLabels() {
		return this.labels;
	}

	@Override
	public List<LanguageSpecificText> getDescriptions() {
		return this.descriptions;
	}

	@Override
	public String getWellKnownText() {
		return this.wellKnownText;
	}

}
