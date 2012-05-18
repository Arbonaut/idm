package org.openforis.idm.transform;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import org.openforis.idm.metamodel.NodeDefinition;

public class ColumnGroup {
	private String shortName;
	private String heading;
	private NodeDefinition nodeDefinition;
	private boolean excluded;
	private ColumnGroup parentGroup;
	private ColumnProvider provider;

	public ColumnGroup() {
	}
	
	public ColumnGroup(String name, String heading, NodeDefinition nodeDefinition, ColumnGroup parentGroup) {
		this.shortName = name;
		this.heading = heading;
		this.nodeDefinition = nodeDefinition;
		this.parentGroup = parentGroup;
	}

	public String getName() {
		StringBuilder sb = new StringBuilder();
		ColumnGroup g = this;
		do {
			String name = g.getShortName();
			if ( isNotEmpty(name) ) {
				if ( sb.length() > 0 ) {
					sb.insert(0, '_');
				}
				sb.insert(0, name);
			}
			g = g.getParentGroup();
		} while ( g != null );
		return sb.toString();
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String name) {
		this.shortName = name;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public NodeDefinition getNodeDefinition() {
		return nodeDefinition;
	}

	public void setNodeDefinition(NodeDefinition nodeDefinition) {
		this.nodeDefinition = nodeDefinition;
	}

	public boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}

	public ColumnGroup getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(ColumnGroup columnGroup) {
		this.parentGroup = columnGroup;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	public ColumnProvider getProvider() {
		return provider;
	}

	public void setProvider(ColumnProvider provider) {
		this.provider = provider;
	}
}
