package test;


import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.colman.om.Mapping;
import com.colman.om.QueryParameter;

public class QueryParameterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQueryParameter1() {
		Serializer serializer = new Persister();
		QueryParameter query = new QueryParameter();
		Mapping mapping1 = new Mapping("testKey1", "testValue2");
		Mapping mapping2 = new Mapping("testKey1", "testValue2");
		query.getMappings().add(mapping1);
		query.getMappings().add(mapping2);
		
		File result = new File("queryParameter.xml");

		try {
			serializer.write(query, result);
			
			assertTrue(result.isFile());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
