package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.CoordinateAttributeDefinition;
import org.openforis.idm.metamodel.DateAttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.RangeAttributeDefinition;
import org.openforis.idm.metamodel.TaxonAttributeDefinition;
import org.openforis.idm.metamodel.TimeAttributeDefinition;
import org.openforis.idm.metamodel.Unit;

/**
 * @author G. Miceli
 * @author M. Togna
 * @deprecated replaced with idm-transform api
 */
@Deprecated
public class AutomaticColumnProvider extends ColumnProviderChain {
	
	private static final Log LOG = LogFactory.getLog(AutomaticColumnProvider.class);
	
	public AutomaticColumnProvider(EntityDefinition entityDefinition, List<String> exclusions) {
		super(createProviders(entityDefinition, exclusions));
	}

	public AutomaticColumnProvider(EntityDefinition entityDefinition) {
		this(entityDefinition, null);
	}
	
	public AutomaticColumnProvider(String headingPrefix, EntityDefinition entityDefinition) {
		this(headingPrefix,  entityDefinition, null);
	}
	
	public AutomaticColumnProvider(String headingPrefix, EntityDefinition entityDefinition, List<String> exclusions) {
		super(headingPrefix, createProviders(entityDefinition, exclusions));
	}
	
	private static List<ColumnProvider> createProviders(EntityDefinition rowDefn, List<String> exclusions) {
		List<ColumnProvider> cols = new ArrayList<ColumnProvider>();
		List<NodeDefinition> childDefinitions = rowDefn.getChildDefinitions();
		for (NodeDefinition childDefn : childDefinitions) {
			if (includeChild(exclusions, childDefn)) {
				if (childDefn instanceof EntityDefinition) {
					createEntityProviders((EntityDefinition) childDefn, cols);
				} else if (childDefn instanceof AttributeDefinition) {
					createAttributeProviders((AttributeDefinition) childDefn, cols);
				}
			}
		}
		return cols;
	}
	
	private static boolean includeChild(List<String> exclusions, NodeDefinition childDefn) {
		return exclusions == null || !exclusions.contains(childDefn.getName());
	}
	
	private static void createEntityProviders(EntityDefinition defn, List<ColumnProvider> cols) {
		String name = defn.getName();
		if ( defn.isMultiple() ) {
			if ( defn.isEnumerable() ) {
				LOG.info("Flatting enumerable multiple entity "+defn.getPath());
				EnumerableEntityColumnProvider p = new EnumerableEntityColumnProvider(defn);
				cols.add(p);
			} else {
				LOG.info("Skipping multiple entity "+defn.getPath());
			}
		} else {
			LOG.info("Flatting single entity "+defn.getPath());
			ColumnProvider p = new AutomaticColumnProvider(defn);
			List<ColumnProvider> childCols = Arrays.asList(p);
			PivotExpressionColumnProvider col = new PivotExpressionColumnProvider(name, name+"_", childCols);
			cols.add(col);
		}
	}

	private static void createAttributeProviders(AttributeDefinition defn, List<ColumnProvider> cols) {
		String name = defn.getName();
		if ( defn.isMultiple() ) {
			LOG.info("Flatting multiple attribute "+defn.getPath());
			MultipleAttributeColumnProvider col = new MultipleAttributeColumnProvider(name, ", ", name);
			cols.add(col);
		} else {
			if(defn instanceof CoordinateAttributeDefinition){
				cols.add(new CoordinateColumnProvider(name));
			} else if(defn instanceof DateAttributeDefinition) {
				cols.add(new DateColumnProvider(name));
			} else if(defn instanceof NumberAttributeDefinition){
				List<Unit> units = ((NumberAttributeDefinition) defn).getUnits();
				if ( units != null && units.size() > 0 ) {
					cols.add(new NumberColumnProvider(name));
				} else {
					cols.add(new SingleAttributeColumnProvider(name, name));
				}
			} else if(defn instanceof RangeAttributeDefinition){
				cols.add(new RangeColumnProvider(name));
			} else if(defn instanceof TimeAttributeDefinition){
				cols.add(new TimeColumnProvider(name));
			} else if(defn instanceof TaxonAttributeDefinition){
				cols.add(new TaxonColumnProvider(name));
			} else if(defn instanceof TimeAttributeDefinition){
				cols.add(new TimeColumnProvider(name));
			} else {
				SingleAttributeColumnProvider col = new SingleAttributeColumnProvider(name, name);
				cols.add(col);
			}
		}
	}
}
