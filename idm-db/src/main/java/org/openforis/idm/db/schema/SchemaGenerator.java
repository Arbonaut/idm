package org.openforis.idm.db.schema;

import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import liquibase.database.core.PostgresDatabase;
import liquibase.database.structure.type.IntType;
import liquibase.database.structure.type.VarcharType;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.core.CreateTableGenerator;
import liquibase.statement.core.CreateTableStatement;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.NodeDefinitionVisitor;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.IdmlBindingContext;
import org.openforis.idm.metamodel.xml.SurveyUnmarshaller;

/**
 * 
 * @author G. Miceli
 *
 */
public class SchemaGenerator {
	
//	public void createSchema(Survey survey) {
//		
//		Schema schema = survey.getSchema();
//		DatabaseSchema dbSchema = new DatabaseSchema("archenland1");
//		List<EntityDefinition> roots = schema.getRootEntityDefinitions();
//		for (EntityDefinition root : roots) {
//			dbSchema.generateTables(root);
//		}
//		
//		schema.traverse(new NodeDefinitionVisitor() {
//			@Override
//			public void visit(NodeDefinition definition) {
//				System.out.println(definition.getName());
//			}
//		});
//		
//		IntType idType = new IntType();
//		VarcharType textType = new VarcharType();
//		textType.setFirstParameter("250");	
//		
//		CreateTableStatement st = new CreateTableStatement("naforma1", "cluster");
//		st.addPrimaryKeyColumn("_id", idType, null, "cluster_pkey", null);
//		st.addColumn("id", textType);
//		st.addColumn("remeasurement", textType);
//		
//		CreateTableGenerator gen = new CreateTableGenerator();
//		PostgresDatabase db = new PostgresDatabase();
////		MySQLDatabase db = new MySQLDatabase();
////		OracleDatabase db = new OracleDatabase();
//		Sql[] sqls = gen.generateSql(st, db, null);
//		for (Sql sql : sqls) {
//			System.out.println(sql.toSql());
//		}
//	}
//
//	public static void main(String[] args)  throws Exception {
//		// Load test IDM
//		URL idm = ClassLoader.getSystemResource("test.idm.xml");
//		InputStream is = idm.openStream();
//		IdmlBindingContext ctx = new IdmlBindingContext();
//		SurveyUnmarshaller unmarshaller = ctx.createSurveyUnmarshaller();
//		Survey survey = unmarshaller.unmarshal(is);
//		survey.setName("archenland1");
//
//		SchemaGenerator schemaFactory = new SchemaGenerator();
//		schemaFactory.createSchema(survey);
//	}
	
}
