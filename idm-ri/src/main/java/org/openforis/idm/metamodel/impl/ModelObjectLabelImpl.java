/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.ModelObjectLabel;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class ModelObjectLabelImpl extends LanguageSpecificTextImpl implements ModelObjectLabel {

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = LabelTypeAdapter.class)
	private LabelType labelType;

	/**
	 * 
	 */
	public ModelObjectLabelImpl() {
	}

	/**
	 * @param language
	 * @param text
	 */
	public ModelObjectLabelImpl(String language, String text) {
		super(language, text);
	}

	public ModelObjectLabelImpl(LabelType labelType, String language, String text) {
		this(language, text);
		this.labelType = labelType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openforis.idm.metamodel.impl.ModelObjectLabel#getLabelType()
	 */
	@Override
	public LabelType getLabelType() {
		return this.labelType;
	}

	private static class LabelTypeAdapter extends XmlAdapter<String, LabelType> {

		@Override
		public LabelType unmarshal(String v) throws Exception {
			return LabelType.valueOf(v.toUpperCase());
		}

		@Override
		public String marshal(LabelType v) throws Exception {
			return v.toString().toLowerCase();
		}
	}

}
