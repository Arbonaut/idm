package org.openforis.idm.transform.csv;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.model.Date;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.Time;
import org.openforis.idm.model.Value;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.openforis.idm.transform.AttributeColumnProvider;
import org.openforis.idm.transform.Cell;
import org.openforis.idm.transform.NodeColumnProvider;
import org.openforis.idm.transform.DateColumnProvider;
import org.openforis.idm.transform.EntityColumnProvider;
import org.openforis.idm.transform.Row;
import org.openforis.idm.transform.TimeColumnProvider;
import org.openforis.idm.transform.Transformation;

/**
 * @author G. Miceli
 */
public class ModelCsvWriter extends CsvWriter {

	public enum HeadingType {
		NODE_NAMES, NODE_LABELS
	};
	
	private Transformation transform;
	private HeadingType headingType;
	private NodeColumnProvider provider;
	private int rowsPrinted;
	
	public ModelCsvWriter(Writer writer, Transformation xform, HeadingType headingType) throws InvalidExpressionException {
		super(writer);
		this.transform = xform;
		this.provider = xform.getRootColumnProvider();
		this.headingType = headingType;
		this.rowsPrinted = 0;
//		prepareProvider(provider);
	}
/*
	protected void prepareProvider(NodeColumnProvider provider) {
		if ( provider instanceof EntityColumnProvider ) {
			List<NodeColumnProvider> childProviders = ((EntityColumnProvider) provider).getProviders();
			for (NodeColumnProvider childProvider : childProviders) {
				prepareProvider(childProvider);
			}
		} else if ( provider instanceof AttributeColumnProvider ) {
			AttributeColumnProvider attrProvider = (AttributeColumnProvider) provider;
			if ( isCollapseable(attrProvider) ) {
				attrProvider.collapseFields();
			} else {
				attrProvider.expandFields();
			}
		}
	}

	protected boolean isCollapseable(AttributeColumnProvider attrProvider) {
		return provider instanceof DateColumnProvider || 
			   provider instanceof TimeColumnProvider;
	}

	public void printHeader() throws IOException {
		List<String> header;
		if ( headingType == HeadingType.NODE_NAMES ) {
			header = provider.getColumnNames();
		} else {
			header = provider.getColumnHeadings();			
		}
		printCsvLine(header);
	}

	protected void printRow(Row row) {
		List<String> values = extractValues(row);
		printCsvLine(values);
	}

	protected List<String> extractValues(Row row) {
		List<Cell> cells = row.getCells();
		List<String> values = new ArrayList<String>(cells.size());
		for (Cell cell : cells) {
			String s = extractValue(cell);
			values.add(s);
		}
		return values;
	}

	protected String extractValue(Cell cell) {
		Object value = cell.getContents();
		System.out.println(value.getClass());
		if ( value instanceof Date ) {
			return ((Date) value).toXmlDate();
		} else if ( value instanceof Time ) {
			return ((Time) value).toXmlTime();
		} else if ( value instanceof Value ) {
			throw new RuntimeException("Unexpected Value of type "+value.getClass().getName());
		} else {
			return value.toString();
		}
	}

	public int printData(Record record) throws InvalidExpressionException {
		int cnt = 0;
		Transformation.Result result = transform.transform(record);
		for ( Row row : result ) {
			printRow(row);
			rowsPrinted++;
		}
		return cnt;
	}

	public HeadingType getHeadingType() {
		return headingType;
	}
	
	public int getRowsPrinted() {
		return rowsPrinted;
	}
	*/
}
