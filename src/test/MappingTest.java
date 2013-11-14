package test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.colman.om.Mapping;

public class MappingTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMapping1() {
		Serializer serializer = new Persister();
		Mapping mapping = new Mapping("testKey", "testValue");
		File result = new File("mapping.xml");

		try {
			serializer.write(mapping, result);
			
			assertTrue(result.isFile());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
