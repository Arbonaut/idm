package org.openforis.idm.model;

import org.openforis.idm.metamodel.FileAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class FileAttribute extends Attribute<FileAttributeDefinition, File> {

	public FileAttribute(FileAttributeDefinition definition) {
		super(definition);
	}

	@Override
	public File createValue(String string) {
		throw new UnsupportedOperationException();
	}
}
