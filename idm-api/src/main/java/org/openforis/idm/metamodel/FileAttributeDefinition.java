/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
@XmlType(name="", propOrder = {"name", "maxSize", "extensions", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", 
		"sinceVersionName", "deprecatedVersionName", "labels", "prompts", "descriptions", "attributeDefaults", "checks" })
public class FileAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;

	private static final FieldDefinition<?>[] FIELD_DEFINITIONS = {
			new FieldDefinition<String>("fileName", "f", null, String.class),
			new FieldDefinition<Long>("fileSize", "s", "size", Long.class)
	};
	
	
	@XmlAttribute(name = "maxSize")
	private Integer maxSize;

	@XmlAttribute(name = "extensions")
	private List<String> extensions;

	public Integer getMaxSize() {
		return this.maxSize;
	}

	public List<String> getExtensions() {
		return CollectionUtil.unmodifiableList(extensions);
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
