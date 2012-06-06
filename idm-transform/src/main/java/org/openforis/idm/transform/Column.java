package org.openforis.idm.transform;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openforis.idm.path.Path;

/**
 * @author G. Miceli
 */
public class Column {

	private ColumnProvider provider;
	private Path path;
	private boolean excluded;
	
	public Column(ColumnProvider provider, Path path) {
		this.provider = provider;
		this.path = path;
	}

	public ColumnProvider getProvider() {
		return provider;
	}

	public Path getPath() {
		return path;
	}

	
	public boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("provider", provider)
			.append("path", path)
			.toString();
	}
}
