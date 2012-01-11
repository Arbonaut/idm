/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Test;
import org.openforis.idm.metamodel.xml.SurveyMarshaller;
import org.openforis.idm.metamodel.xml.SurveyUnmarshaller;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class XmlBindingIntegrationTest {

	@Test
	public void roundTripMarshallingTest() throws IOException {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		SurveyUnmarshaller su = new SurveyUnmarshaller();
		Survey survey = su.unmarshal(is);

		new File("target/test/output").mkdirs();
		FileOutputStream fos = new FileOutputStream("target/test/output/marshalled.idm.xml");
		SurveyMarshaller sm = new SurveyMarshaller();
		sm.marshal(survey, fos, true);
		fos.flush();
		fos.close();
		
//		String name = survey.getName();
//		System.err.println("Survey name " + name);
//
//		Schema schema = survey.getSchema();
//		List<EntityDefinition> rootEntityDefinitions = schema.getRootEntityDefinitions();
//		for (EntityDefinition entityDefinition : rootEntityDefinitions) {
//			System.err.println("root entity: " + entityDefinition.getName());
//			print(entityDefinition, "");
//		}
//
//		Assert.assertTrue(survey.getConfiguration() instanceof Element);
//		System.err.println("~~~END");
	}
//
//	/**
//	 * @return
//	 * @throws JAXBException
//	 * @throws IOException
//	 */
//	private Survey getSurvey() throws JAXBException, IOException {
//		Class<Survey> clazz = Survey.class;
//		URL idm = ClassLoader.getSystemResource("test.idm.xml");
//		InputStream is = idm.openStream();
//		Listener listener = new SurveyUnmarshallerListener();
//		Survey survey = XmlBindingUtil.unmarshall(clazz, is, listener);
//		return survey;
//	}
/*
	private void print(NodeDefinition mod, String p) {
		if (mod instanceof EntityDefinition) {
			System.err.println(p + "Entity " + mod.getName());
			List<NodeDefinition> childDefinitions = ((EntityDefinition) mod).getChildDefinitions();
			for (NodeDefinition nodeDefinition : childDefinitions) {
				print(nodeDefinition, p + "\t");
			}
		} else if (mod instanceof AttributeDefinition) {
			System.out.println("\t" + p + "Attr: " + mod.getName());
		}

	}
*/
	// @Test
//	public void jxpathExprTest() throws JAXBException, IOException {
//		Survey survey = getSurvey();
//		EntityDefinition entityDefinition = (EntityDefinition) survey.getSchema().getRootEntityDefinitions().get(0);
//		EntityDefinition plot = (EntityDefinition) entityDefinition.getChildDefinition("plot");
//		NodeDefinition dbh = plot.get("plot/tree/dbh");
//		System.out.println(dbh.getName());
//		NodeDefinition m2 = dbh.get("parent()");
//		System.out.println(m2.getName());

		// Pointer pointer = jxPathContext.getPointer ("../");
		// System.err.println(pointer.getValue().toString());

		// JXPathContext jxPathContext = JXPathContext.newContext(dbh);
		// Object value = jxPathContext.getValue("../../name");
		// System.out.println(value);

//	}

	// @Test
	// public void testNullable() {
	// A a = new A();
	// B b = new B();
	// a.b = b;
	//
	// CustomTypeConverter converter = new CustomTypeConverter();
	// TypeUtils.setTypeConverter(converter);
	// JXPathContext jxPathContext = JXPathContext.newContext(b);
	//
	// String expr = "(value * 543 div 2345) < 34";
	// Object result = jxPathContext.getValue(expr);
	// System.out.println(result);
	//
	// System.err.println();
	// }

	public static class A {

		B b;

		public B getB() {
			return b;
		}

	}

	public static class B {
		Integer value;

		public B() {

		}

		public Integer getValue() {
			return value;
		}
	}

	// public static class CustomTypeConverter extends BasicTypeConverter {
	//
	// @Override
	// public boolean canConvert(Object object, final Class toType) {
	// if (object == null) {
	// return true;
	// } else
	// return super.canConvert(object, toType);
	// }
	//
	// @Override
	// public Object convert(Object object, Class toType) {
	// // TODO Auto-generated method stub
	// // if(object instanceof Number){
	// //
	// // }
	// return super.convert(object, toType);
	// }
	//
	// }

}
