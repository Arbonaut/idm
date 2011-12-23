/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "dimension", "conversionFactor", "labels", "abbreviations" })
public class Unit extends ModelDefinition {

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "dimension")
	private String dimension;

	@XmlAttribute(name = "conversionFactor")
	private Float conversionFactor;

	@XmlElement(name = "label", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> labels;

	@XmlElement(name = "abbreviation", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> abbreviations;

	public String getName() {
		return this.name;
	}

	public String getDimension() {
		return this.dimension;
	}

	public Number getConversionFactor() {
		return this.conversionFactor;
	}

	public List<LanguageSpecificText> getLabels() {
		return Collections.unmodifiableList(this.labels);
	}

	public List<LanguageSpecificText> getAbbreviations() {
		return Collections.unmodifiableList(this.abbreviations);
	}
}
