package org.openforis.idm.transform;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.DateAttributeDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.DateAttribute;

public class DateColumnProvider extends AttributeColumnProvider {

	public DateColumnProvider(DateAttributeDefinition attributeDefinition, ColumnGroup parentGroup) {
		super(attributeDefinition, parentGroup, true);
	}
//
//	@Override
//	public List<Column> getColumns() {
//		if ( getCollapse() ) {
//			Column col = new Column();
//			AttributeDefinition attrDefn = getAttributeDefinition();
//			col.setParentGroup(getParentGroup());
//			col.setShortName(attrDefn.getName());
//			col.setValueType(Date.class);
//			List<Column> cols = Arrays.asList(col);
//			return Collections.unmodifiableList(cols);
//		} else {
//			return super.getColumns();
//		}
//	}
//	
//	@Override
//	protected List<Cell> toCells(Attribute<?, ?> attr) {
//		if ( getCollapse() ) {
//			DateAttribute dateAttr = (DateAttribute) attr;
////			Date javaDate = dateAttr.getValue() == null ? null : dateAttr.getValue().toJavaDate();
//			Object value = attr.getValue();
//			Cell cell = new Cell(value, null, attr);
//			return Arrays.asList(cell);
//		} else {
//			return super.toCells(attr);
//		}
//	}
}
