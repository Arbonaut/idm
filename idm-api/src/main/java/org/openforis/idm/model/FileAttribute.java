package org.openforis.idm.model;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.FileAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class FileAttribute extends Attribute<FileAttributeDefinition, File> {

	public FileAttribute(FileAttributeDefinition definition) {
		super(definition, 0);
	}

	@Override
	public File createValue(String string) {
		throw new UnsupportedOperationException();
	}
	
	@SuppressWarnings("unchecked")
	public Field<String> getFilenameField() {
		return (Field<String>) getField(0);
	}
	
	@SuppressWarnings("unchecked")
	public Field<Long> getSizeField() {
		return (Field<Long>) getField(0);
	}
	
	@Override
	public File getValue() {
		String filename = getFilenameField().getValue();
		Long size = getSizeField().getValue();
		return new File(filename, size);
	}

	@Override
	public void setValue(File value) {
		String filename = value.getFilename();
		Long size = value.getSize();
		getFilenameField().setValue(filename);
		getSizeField().setValue(size);
	}
	
	@Override
	public boolean isEmpty() {
		File f = getValue();
		return f == null || (f.getSize() == null && StringUtils.isBlank(f.getFilename()));
	}
}
