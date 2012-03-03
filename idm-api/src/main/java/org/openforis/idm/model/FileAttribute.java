package org.openforis.idm.model;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.FileAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class FileAttribute extends Attribute<FileAttributeDefinition, File> {

	private static final long serialVersionUID = 1L;

	FileAttribute() {
	}
	
	public FileAttribute(FileAttributeDefinition definition) {
		super(definition, String.class, Long.class);
	}
	
	@SuppressWarnings("unchecked")
	public AttributeField<String> getFilenameField() {
		return (AttributeField<String>) getField(0);
	}
	
	@SuppressWarnings("unchecked")
	public AttributeField<Long> getSizeField() {
		return (AttributeField<Long>) getField(1);
	}
	
	@Override
	public File getValue() {
		String filename = getFilenameField().getValue();
		Long size = getSizeField().getValue();
		return new File(filename, size);
	}

	@Override
	public void setValue(File value) {
		if ( value == null ) {
			clearValue();
		} else {
			String filename = value.getFilename();
			Long size = value.getSize();
			getFilenameField().setValue(filename);
			getSizeField().setValue(size);
		}
	}
	
	@Override
	public boolean isEmpty() {
		File f = getValue();
		return f == null || (f.getSize() == null && StringUtils.isBlank(f.getFilename()));
	}
}
