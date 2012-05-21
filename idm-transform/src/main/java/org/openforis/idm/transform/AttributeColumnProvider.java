package org.openforis.idm.transform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.DateAttributeDefinition;
import org.openforis.idm.metamodel.FieldDefinition;
import org.openforis.idm.metamodel.NumberAttributeDefinition;
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
	private boolean expanded;

	public AttributeColumnProvider(AttributeDefinition attributeDefinition, ColumnGroup parentGroup) {
		super();
		this.parentGroup = parentGroup;
		this.attributeDefinition = attributeDefinition;
		this.expanded = false;
	}

	protected ColumnProviderChain getFieldProviderChain() {
		if ( fieldProviderChain == null ) {
			fieldProviderChain = createFieldProviderChain();
		}
		return fieldProviderChain;
	}
	
	protected ColumnProviderChain createFieldProviderChain() {
		String name = attributeDefinition.getName();
		List<FieldDefinition> fieldDefinitions = attributeDefinition.getFieldDefinitions();
		ColumnProviderChain chain = new ColumnProviderChain(); 
		if ( fieldDefinitions.size() == 1 ) {
			FieldDefinition fldDefn = fieldDefinitions.get(0);
			FieldColumnProvider fcp = new FieldColumnProvider(fldDefn, getParentGroup(), attributeDefinition.getName());
			// TODO col.setHeading()
			chain.addProvider(fcp);
		} else {
			ColumnGroup columnGroup = new ColumnGroup(name, null, attributeDefinition, parentGroup, this); // TODO heading	
			for (FieldDefinition defn : fieldDefinitions) {
				// TODO col.setHeading()
				FieldColumnProvider fcp = new FieldColumnProvider(defn, columnGroup);
				chain.addProvider(fcp);
			}
		}
		return chain;
	}

	@Override
	public List<Column> getColumns() {
		if ( isExpanded() ) {
			return getExpandedColumns();
		} else {
			return getCollapsedColumns();
		}	
	}

	protected List<Column> getCollapsedColumns() {
		AttributeDefinition attrDefn = getAttributeDefinition();
		String shortName = attrDefn.getName();
		Class<?> type = attrDefn.getValueType();
		// TODO Heading
		Column col = new Column(shortName, null, attrDefn, getParentGroup(), this, type);
		List<Column> cols = Arrays.asList(col);
		return cols;
	}

	protected List<Column> getExpandedColumns() {
		ColumnProviderChain chain = getFieldProviderChain();
		return chain.getColumns();
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
			if ( attr == null ) {
				return getEmptyCells();
			} if ( isExpanded() ) {
				cells = getExpandedCells(attr);
			} else {
				cells = getCollapsedCells(attr);
			}
			return Collections.unmodifiableList(cells);
		} else {
			throw new IllegalArgumentException("Expected "+Entity.class+" but got "+parent.getClass());
		}
	}

	protected List<Cell> getCollapsedCells(Attribute<?,?> attr) {
		Object value = attr.getValue();
		AttributeDefinition defn = attr.getDefinition();
		Cell cell = new Cell(value, defn.getValueType(), attr);
		return Arrays.asList(cell);
	}

	protected List<Cell> getExpandedCells(Attribute<?,?> attr) {
		ColumnProviderChain chain = getFieldProviderChain();
		return chain.getCells(attr);
	}
	
	public AttributeDefinition getAttributeDefinition() {
		return attributeDefinition;
	}
	
	public ColumnGroup getParentGroup() {
		return parentGroup;
	}
	
	public boolean isExpanded() {
		return expanded;
	}

	public void expand() {
		fieldProviderChain = null;
		this.expanded = true;
	}

	public void collapse() {
		fieldProviderChain = null;
		this.expanded = false;
	}
	
	public static <T extends AttributeDefinition> AttributeColumnProvider createInstance(T definition, ColumnGroup columnGroup) {
		if ( definition instanceof NumberAttributeDefinition ) {
			return new NumberColumnProvider((NumberAttributeDefinition) definition, columnGroup);
		} else if ( definition instanceof DateAttributeDefinition ) {
			return new DateColumnProvider((DateAttributeDefinition) definition, columnGroup);
		} else if ( definition instanceof TimeAttributeDefinition ) {
			return new TimeColumnProvider((TimeAttributeDefinition) definition, columnGroup);
		} else if ( definition instanceof CodeAttributeDefinition ) { 
			return new CodeColumnProvider((CodeAttributeDefinition) definition, columnGroup);
		} else {
			return new AttributeColumnProvider(definition, columnGroup);
//			throw new UnsupportedOperationException("ColumnProvider for "+definition.getClass()+" not implemented");
		}
	}
}
