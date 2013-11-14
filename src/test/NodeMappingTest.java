package test;


import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.colman.om.Mapping;
import com.colman.om.NodeMapping;

public class NodeMappingTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNodeMapping1() {
		Serializer serializer = new Persister();
		NodeMapping nodeMapping = new NodeMapping();
		Mapping mapping1 = new Mapping("testKey1", "testValue2");
		Mapping mapping2 = new Mapping("testKey1", "testValue2");
		nodeMapping.getNodeMappingList().add(mapping1);
		nodeMapping.getNodeMappingList().add(mapping2);
		
		File result = new File("nodeMapping.xml");

		try {
			serializer.write(nodeMapping, result);
			
			assertTrue(result.isFile());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
