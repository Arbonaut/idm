package org.openforis.idm.transform;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 */
public class ValueColumnProvider extends SingleColumnProvider {

	private AttributeDefinition attributeDefinition;
	private Heading parentHeading;
	
	public ValueColumnProvider(AttributeDefinition attributeDefinition, Heading parentHeading) {		
		this.attributeDefinition = attributeDefinition;
		this.parentHeading = parentHeading;
	}

	public Column getColumn() {
		String name = attributeDefinition.getName();
		// TODO attach to defn
		// TODO assign label
		Heading heading = new Heading(name, null, null, parentHeading, this);
		return new Column(heading);
	}

	@Override
	public Cell<?> getCell(Node<?> attribute) {
		Column column = getColumn();
		if ( attribute == null ) {
			return new EmptyCell(column);
		} else if ( attribute instanceof Attribute ) {
			Attribute<?,?> attr = (Attribute<?,?>) attribute;
			SingleValueCell cell = new SingleValueCell(column, attr);
			return cell;
		} else {
			throw new IllegalArgumentException("Expected "+Attribute.class+" but got "+attribute.getClass());
		}
	}

	public AttributeDefinition getAttributeDefinition() {
		return attributeDefinition;
	}
}
