package org.openforis.idm.model.impl;

import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RecordManagerTest {
	
    private static Logger logger = Logger.getLogger(RecordManagerTest.class.getName());

    @BeforeClass
    protected void setUp() throws Exception {
        try {
            logger.info("Starting in-memory database for unit tests");
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            DriverManager.getConnection("jdbc:derby:memory:unit-testing-jpa;create=true").close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail("Exception during database startup.");
        }
    }


    @AfterClass
    protected void tearDown() throws Exception {
        logger.info("Stopping in-memory database.");
        try {
            DriverManager.getConnection("jdbc:derby:memory:unit-testing-jpa;shutdown=true").close();
        } catch (SQLNonTransientConnectionException ex) {
            if (ex.getErrorCode() != 45000) {
                throw ex;
            }
            // Shutdown success
        }
//        VFMemoryStorageFactory.purgeDatabase(new File("unit-testing-jpa").getCanonicalPath());
    }
    
    @Test
    public void testPersistence() {
        try {
            
//            g.removeUser(u);
//            em.remove(u);
//            em.merge(g);
//            assertFalse(em.contains(u));
//
//            em.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail("Exception during testPersistence");
        }
    }
}
