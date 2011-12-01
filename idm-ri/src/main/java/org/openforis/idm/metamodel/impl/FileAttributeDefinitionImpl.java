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

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.FileAttributeDefinition;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class FileAttributeDefinitionImpl extends AbstractAttributeDefinition implements FileAttributeDefinition {

	@XmlAttribute(name = "maxSize")
	private Integer maxSize;

	@XmlAttribute(name = "extensions")
	@XmlJavaTypeAdapter(value = ExtensionsTypeAdapter.class)
	private String[] extensions;

	@Override
	public Integer getMaxSize() {
		return this.maxSize;
	}

	@Override
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public String[] getExtensions() {
		return this.extensions;
	}

	@Override
	public void setExtensions(String[] extensions) {
		this.extensions = extensions;
	}

	public static class ExtensionsTypeAdapter extends XmlAdapter<String, String[]> {

		@Override
		public String[] unmarshal(String v) throws Exception {
			String[] strings = null;
			if (StringUtils.isNotBlank(v)) {
				strings = v.split(" ");
			}
			return strings;
		}

		@Override
		public String marshal(String[] v) throws Exception {
			return StringUtils.join(v, " ");
		}

	}

}
