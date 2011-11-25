/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import org.openforis.idm.metamodel.MultilingualStringMap;
import org.openforis.idm.metamodel.Unit;

/**
 * @author Mino Togna
 * 
 */
public class UnitImpl implements Unit {
	private MultilingualStringMap labels;
	private MultilingualStringMap abbreviations;
	private String name;
	private String dimension;
	private Number conversionFactor;

	public MultilingualStringMap getLabels() {
		return labels;
	}

	public void setLabels(MultilingualStringMap labels) {
		this.labels = labels;
	}

	public MultilingualStringMap getAbbreviations() {
		return abbreviations;
	}

	public void setAbbreviations(MultilingualStringMap abbreviations) {
		this.abbreviations = abbreviations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public Number getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(Number conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
}
