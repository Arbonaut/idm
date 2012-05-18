package org.openforis.idm.db.schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;

import liquibase.statement.core.CreateTableStatement;

public class DatabaseSchema {
	private String name;
	private Map<String, CreateTableStatement> tables;
	
	public DatabaseSchema(String name) {
		this.name = name;
		this.tables = new HashMap<String, CreateTableStatement>();
	}

	public void generateTables(EntityDefinition defn) {
		if ( !defn.isMultiple() ) {
			throw new IllegalArgumentException("Definition must be a multiple entity");
		}
		
		generateTable(defn);
	}

	private void generateTable(EntityDefinition defn) {
		// Recursively gather descendant single attributes and entities
		List<NodeDefinition> children = defn.getChildDefinitions();
		for (NodeDefinition child : children) {
			
		}
	}
}
