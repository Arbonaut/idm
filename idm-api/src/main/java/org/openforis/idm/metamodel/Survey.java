package org.openforis.idm.metamodel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Unmarshaller.Listener;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Element;
import org.xml.sax.ContentHandler;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@SuppressWarnings("deprecation")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "projectNames", "cycle", "descriptions", "configuration", "modelVersions",
		"codeLists", "units", "spatialReferenceSystems", "schema" })
@XmlRootElement(name = "survey")
public class Survey {

	@XmlTransient
	private Integer id;
	
	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "project", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> projectNames;

	@XmlElement(name = "cycle")
	private Integer cycle;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(name = "configuration")
	@XmlJavaTypeAdapter(value = ElementTypeAdapter.class)
	private Element configuration;

	@XmlElement(name = "version", type = ModelVersion.class)
	@XmlElementWrapper(name = "versioning")
	private List<ModelVersion> modelVersions;

	@XmlElement(name = "list", type = CodeList.class)
	@XmlElementWrapper(name = "codeLists")
	private List<CodeList> codeLists;

	@XmlElement(name = "unit", type = Unit.class)
	@XmlElementWrapper(name = "units")
	private List<Unit> units;

	@XmlElement(name = "spatialReferenceSystem", type = SpatialReferenceSystem.class)
	@XmlElementWrapper(name = "spatialReferenceSystems")
	private List<SpatialReferenceSystem> spatialReferenceSystems;

	@XmlElement(name = "schema", type = Schema.class)
	private Schema schema;

	public Integer getId() {
		return id;
	}
	
	// TODO Encapsulate this better (e.g. using reflection or subclass)
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}

	public List<LanguageSpecificText> getProjectNames() {
		return Collections.unmodifiableList(this.projectNames);
	}

	public Integer getCycle() {
		return this.cycle;
	}

	public List<LanguageSpecificText> getDescriptions() {
		return Collections.unmodifiableList(this.descriptions);
	}

	public Element getConfiguration() {
		return this.configuration;
	}
	
	public List<ModelVersion> getVersions() {
		return Collections.unmodifiableList(this.modelVersions);
	}

	public List<CodeList> getCodeLists() {
		return Collections.unmodifiableList(this.codeLists);
	}

	public List<Unit> getUnits() {
		return Collections.unmodifiableList(this.units);
	}

	public List<SpatialReferenceSystem> getSpatialReferenceSystems() {
		return Collections.unmodifiableList(this.spatialReferenceSystems);
	}

	public Schema getSchema() {
		return this.schema;
	}
	
	/**
	 * Passes DOM Element directly without conversion
	 */
	private static class ElementTypeAdapter extends XmlAdapter<Object, Object> {
		@Override
		public Object unmarshal(Object v) {
			return v;
		}

		@Override
		public Object marshal(Object v) throws Exception {
			return v;
		}
	}

	public ModelVersion getVersion(String name) {
		if ( modelVersions != null ) {
			for (ModelVersion v : modelVersions) {
				if ( name.equals(v.getName()) ) {
					return v;
				}
			}
		}
		return null;
	}

	public CodeList getCodeList(String name) {
		for (CodeList codeList : codeLists) {
			if (codeList.getName().equals(name)) {
				return codeList;
			}
		}
		return null;
	}

	public Unit getUnit(String name) {
		for (Unit unit : units) {
			if (unit.getName().equals(name)) {
				return unit;
			}
		}
		return null;
	}
	
	public static Survey unmarshal(InputStream is) throws IOException {
		try {
			JAXBContext jc = JAXBContext.newInstance(Survey.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			Listener listener = new UnmarshallerListener();
			unmarshaller.setListener(listener);
			Survey survey = (Survey) unmarshaller.unmarshal(is);
			return survey;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static class UnmarshallerListener extends Unmarshaller.Listener {
		@Override
		public void beforeUnmarshal(Object target, Object parent) {
			try {
				if ( target instanceof Schema ) {
					beforeUnmarshal((Schema) target, parent);
				}
				if ( target instanceof CodeList ) {
					beforeUnmarshal((CodeList) target, parent);
				}
				if ( target instanceof CodeListItem ) {
					beforeUnmarshal((CodeListItem) target, parent);
				}
				if (target instanceof NodeDefinition ) {
					beforeUnmarshal((NodeDefinition) target, parent);
				}
				if (target instanceof Precision) {
					beforeUnmarshal((Precision) target, parent);
				}
			} catch ( ClassCastException ce ) {
				throw new RuntimeException("Unexpected sub-element while unmarshalling", ce);
			}
		}
		
		private void beforeUnmarshal(NodeDefinition target, Object parent) {
			if ( parent instanceof EntityDefinition ) { 
				target.setParentDefinition((EntityDefinition) parent);
			} else {
				target.setSchema((Schema) parent);
			}
		}

		private void beforeUnmarshal(CodeList target, Object parent) {
			target.setSurvey((Survey) parent);
		}

		private void beforeUnmarshal(CodeListItem target, Object parent) {
			if ( parent instanceof CodeListItem ) {
				target.setParentItem((CodeListItem) parent);
			} else if ( parent instanceof CodeList ) {
				target.setList((CodeList) parent);
			}
		}

		private void beforeUnmarshal(Schema target, Object parent) {
			target.setSurvey((Survey) parent);
		}

		private void beforeUnmarshal(Precision target, Object parent) {
			target.setDefinition((NumericAttributeDefinition) parent);
		}

		@Override
		public void afterUnmarshal(Object target, Object parent) {
			if (target instanceof NodeDefinition ) {
				afterUnmarshal((NodeDefinition) target, parent);
			}
		}
		
		private void afterUnmarshal(NodeDefinition target, Object parent) {
			target.getSchema().indexByPath(target);
		}

	}

	public void marshal(OutputStream os, boolean indent) throws IOException {
		try {
			JAXBContext jc = JAXBContext.newInstance(Survey.class);
			Marshaller marshaller = jc.createMarshaller();
//			marshaller.setProperty("jaxb.formatted.output", true);
//			marshaller.setProperty("jaxb.encoding", "UTF-8");

			// JAXP Transformer not respecting CDATA_SECTION_ELEMENTS
//			SAXTransformerFactory tfactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
//			TransformerHandler handler = tfactory.newTransformerHandler();
//			Transformer t = handler.getTransformer();
//			t.setOutputProperty(OutputKeys.METHOD, "xml");
//			t.setOutputProperty(OutputKeys.INDENT, "no");
//			t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//			t.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "{http://www.openforis.org/idml/3.0}wkt");
//			Result outputResult = new StreamResult(System.out);
//			handler.setResult(outputResult);
			
			// Using deprecated Xerces form now...
			OutputFormat of = new OutputFormat();
			of.setCDataElements(new String[] { "http://www.openforis.org/idml/3.0^wkt" }); //
			of.setEncoding("UTF-8");
			of.setIndenting(indent);
			XMLSerializer serializer1 = new XMLSerializer(of);
			serializer1.setOutputByteStream(os);
			XMLSerializer serializer = serializer1;
			ContentHandler handler = serializer.asContentHandler();
			
			marshaller.marshal(this, handler);
			// marshaller.marshal(this, os);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}