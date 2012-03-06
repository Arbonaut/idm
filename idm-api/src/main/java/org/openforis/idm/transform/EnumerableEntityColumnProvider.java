package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 */
public class EnumerableEntityColumnProvider extends ColumnProviderChain {

	// TODO Support multiple code keys 
	// TODO Check that def is right type!
	// TODO Check that list is not lookup
	// TODO support hierarchical lists!

	public EnumerableEntityColumnProvider(EntityDefinition defn) {
		super(defn.getName()+"_", createProviders(defn));
	}

	private static List<ColumnProvider> createProviders(EntityDefinition defn) {
		List<ColumnProvider> providers = new ArrayList<ColumnProvider>();
		List<AttributeDefinition> keyDefs = defn.getKeyAttributeDefinitions();
		CodeAttributeDefinition keyDef = (CodeAttributeDefinition) keyDefs.get(0);
		CodeList codeList = keyDef.getList();
		List<CodeListItem> items = codeList.getItems();
//		String entityName = defn.getName();
		for (CodeListItem item : items) {
			String code = item.getCode();
			String keyName = keyDef.getName();
			PerCodeColumnProvider p = new PerCodeColumnProvider(defn, keyName, code);
			providers.add(p);
		}
		return providers;
	}

	private static class PerCodeColumnProvider extends AutomaticColumnProvider {

		private EntityDefinition entityDefinition;
		private String keyName;
		private String code;

		public PerCodeColumnProvider(EntityDefinition defn, String keyName, String code) {
			super(code+"_", defn);
			this.entityDefinition = defn;
			this.keyName = keyName;
			this.code = code;
		}
		
		@Override
		public List<String> extractValues(Node<?> axis) {
			Entity parentEntity = (Entity) axis;
			List<Node<?>> children = parentEntity.getAll(entityDefinition.getName());
			for (Node<?> child : children) {
				Entity childEntity = (Entity) child;
				Code childCode = (Code) childEntity.getValue(keyName, 0);
				String codeVal = childCode.getCode();
				if ( code.equals(codeVal) ) {
					return super.extractValues(childEntity);
				}
			}
			// not found; return empty array
			return super.emptyValues();
		}
	}
}
