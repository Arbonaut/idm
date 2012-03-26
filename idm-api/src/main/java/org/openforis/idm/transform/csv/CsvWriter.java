package org.openforis.idm.transform.csv;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

/**
 * @author G. Miceli
 */
public class CsvWriter extends PrintWriter {

	public CsvWriter(Writer out) {
		super(out, true);
	}

	public void printCsvLine(List<String> values) {
		boolean first = true;
		for (String v : values) {
			if (first) {
				first = false;
			} else {
				print(',');
			}
			if ( v == null ) {
				v = "";
			} else {
				v = v.replace("\"", "\"\"");
			}
			print('"');
			print(v);
			print('"');
		}
		println();
	}
}