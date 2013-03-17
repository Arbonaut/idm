/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openforis.commons.collection.CollectionUtils;
import org.openforis.idm.model.File;
import org.openforis.idm.model.FileAttribute;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */

public class FileAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;
	
	private final FieldDefinition<?>[] FIELD_DEFINITIONS = {
			new FieldDefinition<String>("fileName", "f", null, String.class, this),
			new FieldDefinition<Long>("fileSize", "s", "size", Long.class, this)
	};
	
	private Integer maxSize;
	private List<String> extensions;
	
	FileAttributeDefinition(Survey survey, int id) {
		super(survey, id);
	}

	public Integer getMaxSize() {
		return this.maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
	
	public List<String> getExtensions() {
		return CollectionUtils.unmodifiableList(extensions);
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
