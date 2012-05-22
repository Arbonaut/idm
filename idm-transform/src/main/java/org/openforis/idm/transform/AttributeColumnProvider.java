package org.openforis.idm.transform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.BooleanAttributeDefinition;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CoordinateAttributeDefinition;
import org.openforis.idm.metamodel.DateAttributeDefinition;
import org.openforis.idm.metamodel.FieldDefinition;
import org.openforis.idm.metamodel.FileAttributeDefinition;
import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.RangeAttributeDefinition;
import org.openforis.idm.metamodel.TaxonAttributeDefinition;
import org.openforis.idm.metamodel.TextAttributeDefinition;
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
public abstract class AttributeColumnProvider extends ColumnProvider {

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
		List<Column> cols = getCollapsedColumns();
		Cell cell = new Cell(value, defn.getValueType(), cols.get(0), attr);
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
		this.expanded = true;
	}

	public void collapse() {
		this.expanded = false;
	}

	public static <T extends AttributeDefinition> AttributeColumnProvider createInstance(T defn, ColumnGroup grp) {
		if ( defn instanceof NumberAttributeDefinition ) {
			return new NumberColumnProvider((NumberAttributeDefinition) defn, grp);
		} else if ( defn instanceof BooleanAttributeDefinition ) { 
			return new BooleanColumnProvider((BooleanAttributeDefinition) defn, grp);
		} else if ( defn instanceof CodeAttributeDefinition ) { 
			return new CodeColumnProvider((CodeAttributeDefinition) defn, grp);
		} else if ( defn instanceof TextAttributeDefinition ) { 
			return new TextColumnProvider((TextAttributeDefinition) defn, grp);
		} else if ( defn instanceof DateAttributeDefinition ) {
			return new DateColumnProvider((DateAttributeDefinition) defn, grp);
		} else if ( defn instanceof TimeAttributeDefinition ) {
			return new TimeColumnProvider((TimeAttributeDefinition) defn, grp);
		} else if ( defn instanceof RangeAttributeDefinition ) { 
			return new RangeColumnProvider((RangeAttributeDefinition) defn, grp);
		} else if ( defn instanceof TaxonAttributeDefinition ) { 
			return new TaxonColumnProvider((TaxonAttributeDefinition) defn, grp);
		} else if ( defn instanceof CoordinateAttributeDefinition ) { 
			return new CoordinateColumnProvider((CoordinateAttributeDefinition) defn, grp);
		} else if ( defn instanceof FileAttributeDefinition ) { 
			return new FileColumnProvider((FileAttributeDefinition) defn, grp);
		} else {
			throw new UnsupportedOperationException("ColumnProvider for "+defn.getClass()+" not implemented");
		}
	}
}
