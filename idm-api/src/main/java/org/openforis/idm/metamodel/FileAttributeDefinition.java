/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.model.File;
import org.openforis.idm.model.FileAttribute;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Value;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"id", "name", "maxSize", "extensions", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", 
		"sinceVersionName", "deprecatedVersionName", "labels", "prompts", "descriptions", "attributeDefaults", "checks" })
public class FileAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private final FieldDefinition<?>[] FIELD_DEFINITIONS = {
			new FieldDefinition<String>("fileName", "f", null, String.class, this),
			new FieldDefinition<Long>("fileSize", "s", "size", Long.class, this)
	};
	
	
	@XmlAttribute(name = "maxSize")
	private Integer maxSize;

	@XmlAttribute(name = "extensions")
	private List<String> extensions;

	public Integer getMaxSize() {
		return this.maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
	
	public List<String> getExtensions() {
		return CollectionUtil.unmodifiableList(extensions);
	}
	
	public void addExtension(String extension) {
		if ( extensions == null )  {
			extensions = new ArrayList<String>();
		}
		extensions.add(extension);
	}

	public void addExtensions(List<String> extensions) {
		if ( extensions != null ) {
			for (String extension : extensions) {
				addExtension(extension);
			}
		}
	}
	
	public void removeExtension(String extension) {
		extensions.remove(extension);
	}
	
	public void removeExtensions(List<String> extensions) {
		if (extensions != null ) {
			extensions.removeAll(extensions);
		}
	}
	
	public void removeAllExtensions() {
		if ( extensions == null ) {
			extensions = new ArrayList<String>();
		} else {
			extensions.clear();
		}
	}

	@Override
	public Node<?> createNode() {
		return new FileAttribute(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public File createValue(String string) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public List<FieldDefinition<?>> getFieldDefinitions() {
		return Collections.unmodifiableList(Arrays.asList(FIELD_DEFINITIONS));
	}

	@Override
	public Class<? extends Value> getValueType() {
		return File.class;
	}
}
