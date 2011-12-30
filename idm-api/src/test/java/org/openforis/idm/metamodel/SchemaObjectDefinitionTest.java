package org.openforis.idm.metamodel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;

/**
 * @author G. Miceli
 */
public class SchemaObjectDefinitionTest {

	@Test
	public void testGetPathAtRoot() {
		SchemaObjectDefinition mock = mock(SchemaObjectDefinition.class);
		
		when(mock.getName()).thenReturn("cluster");
		doCallRealMethod().when(mock).getPath();
		
		String path = mock.getPath();
		assertEquals("/cluster", path);
	}

	@Test
	public void testGetPathAtSecondLevel() {
		EntityDefinition parentMock = mock(EntityDefinition.class);
		SchemaObjectDefinition mock = mock(SchemaObjectDefinition.class);
		
		when(parentMock.getName()).thenReturn("cluster");
		when(mock.getName()).thenReturn("plot");
		when(mock.getParentDefinition()).thenReturn(parentMock);
		doCallRealMethod().when(mock).getPath();
		
		String path = mock.getPath();
		assertEquals("/cluster/plot", path);
	}
}
