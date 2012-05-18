package org.openforis.idm.transform;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.DateAttributeDefinition;
import org.openforis.idm.metamodel.FieldDefinition;
import org.openforis.idm.metamodel.TimeAttributeDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;

/** 
 * Base class provides one more columns for an attribute
 *  
 * @author G. Miceli
 *
 */
public class AttributeColumnProvider extends ColumnProvider {
//	public enum Units {
//		INCLUDE_ALL, INCLUDE_AMBIGUOUS, EXCLUDE_ALL, NORMALIZE   
//	}
	
	private AttributeDefinition attributeDefinition;
	private ColumnProviderChain fieldProviderChain;
	private ColumnGroup parentGroup;
	private boolean collapse;

	public AttributeColumnProvider(AttributeDefinition attributeDefinition, ColumnGroup parentGroup, boolean collapseDefault) {
		super();
		this.parentGroup = parentGroup;
		this.attributeDefinition = attributeDefinition;
		this.collapse = collapseDefault;
//		String name = attributeDefinition.getName();
//		this.columnGroup = new ColumnGroup(name, null, attributeDefinition, parentGroup); // TODO heading	
//		if ( attributeDefinition instanceof CodeAttributeDefinition ) {
//			CodeAttributeDefinition defn = (CodeAttributeDefinition) attributeDefinition;
//			fieldProviderChain.addProvider(new FieldColumnProvider(defn.getFieldDefinition("code"), columnGroup));
//			CodeList list = defn.getList();
//			if ( list.isQualifiable() ) {
//				addProvider(new FieldColumnProvider(defn.getFieldDefinition("qualifier"), columnGroup));				
//			}
//		} else if ( attributeDefinition instanceof DateAttributeDefinition ) { 
//		} else {
//			this.fieldProviderChain = new ColumnProviderChain(); 
//			List<FieldDefinition> fieldDefinitions = attributeDefinition.getFieldDefinitions();
//			for (FieldDefinition defn : fieldDefinitions) {
//				// TODO col.setHeading()
//				FieldColumnProvider fcp = new FieldColumnProvider(defn, columnGroup);
//				fieldProviderChain.addProvider(fcp);
//			}
//		}
	}

//	protected abstract List<Cell> toCells(Attribute<?, ?> attr);

	protected ColumnProviderChain getFieldProviderChain() {
		if ( fieldProviderChain == null ) {
			fieldProviderChain = createFieldProviderChain();
		}
		return fieldProviderChain;
	}
	
	protected ColumnProviderChain createFieldProviderChain() {
		String name = attributeDefinition.getName();
		ColumnGroup columnGroup = new ColumnGroup(name, null, attributeDefinition, parentGroup); // TODO heading	
		ColumnProviderChain chain = new ColumnProviderChain(); 
		List<FieldDefinition> fieldDefinitions = attributeDefinition.getFieldDefinitions();
		for (FieldDefinition defn : fieldDefinitions) {
			// TODO col.setHeading()
			FieldColumnProvider fcp = new FieldColumnProvider(defn, columnGroup);
			chain.addProvider(fcp);
		}
		return chain;
	}

	@Override
	public List<Column> getColumns() {
		if ( getCollapse() ) {
			return collapseColumns();
		} else {
			return expandColumns();
		}	
	}

	@Override
	public List<Cell> getCells(Node<?> parent) {
		if ( parent == null ) {
			return getEmptyCells();
		} else if ( parent instanceof Entity ) {
			Entity parentEntity = (Entity) parent;
			String name = attributeDefinition.getName();
			Attribute<?, ?> attr = (Attribute<?, ?>) parentEntity.get(name, 0);
			List<Cell> cells; 
			if ( getCollapse() ) {
				cells = collapseCells(attr);
			} else {
				cells = expandCells(attr);
			}
			return Collections.unmodifiableList(cells);
		} else {
			throw new IllegalArgumentException("Expected "+Entity.class+" but got "+parent.getClass());
		}
	}

	protected List<Column> collapseColumns() {
		Column col = new Column();
		AttributeDefinition attrDefn = getAttributeDefinition();
		col.setParentGroup(getParentGroup());
		col.setShortName(attrDefn.getName());
		col.setValueType(Date.class);
		List<Column> cols = Arrays.asList(col);
		return cols;
	}

	protected List<Column> expandColumns() {
		ColumnProviderChain chain = getFieldProviderChain();
		return chain.getColumns();
	}

	protected List<Cell> collapseCells(Attribute<?,?> attr) {
		Object value = attr.getValue();
		AttributeDefinition defn = attr.getDefinition();
		Cell cell = new Cell(value, defn.getValueType(), attr);
		return Arrays.asList(cell);
	}

	protected List<Cell> expandCells(Attribute<?,?> attr) {
		ColumnProviderChain chain = getFieldProviderChain();
		return chain.getCells(attr);
	}
	
	public AttributeDefinition getAttributeDefinition() {
		return attributeDefinition;
	}
	
	public ColumnGroup getParentGroup() {
		return parentGroup;
	}
	

	public boolean getCollapse() {
		return collapse;
	}

	public void setCollapse(boolean collapse) {
		this.collapse = collapse;
	}
	
	public static <T extends AttributeDefinition> AttributeColumnProvider createInstance(T definition, ColumnGroup columnGroup) {
		if ( definition instanceof DateAttributeDefinition ) {
			return new DateColumnProvider((DateAttributeDefinition) definition, columnGroup);
		} else if ( definition instanceof TimeAttributeDefinition ) {
				return new TimeColumnProvider((TimeAttributeDefinition) definition, columnGroup);
		} else if ( definition instanceof CodeAttributeDefinition ) { 
			return new CodeColumnProvider((CodeAttributeDefinition) definition, columnGroup);
		} else {
			return new AttributeColumnProvider(definition, columnGroup, false);
//			throw new UnsupportedOperationException("ColumnProvider for "+definition.getClass()+" not implemented");
		}
	}
}
