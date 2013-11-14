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
import com.colman.om.QueryParameter;
import com.colman.om.Recreation;
import com.colman.om.queryParameterPrompt;

public class RecreationTest {

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
		Recreation recreation = new Recreation();

		recreation.setHost("app06.ottawa.ca");
		recreation.setPort("-1");
		recreation.setPath("/cgi-bin/schedulesearch/searchschedule.pl");
		recreation.setName("Swimming");
		recreation.setParameter(query);
		NodeMapping nodeMapping = new NodeMapping();
		Mapping node = new Mapping("day", "arr");
		nodeMapping.getNodeMappingList().add(node);
		recreation.setNodeMapping(nodeMapping);
		queryParameterPrompt uiComponent = new queryParameterPrompt();
		Mapping ui = new Mapping("day", "Day.xml");
		uiComponent.getQueryParameterPromptList().add(ui);
		recreation.setQueryParameterPrompt(uiComponent);

		File result = new File("recreation.xml");

		try {
			serializer.write(recreation, result);

			assertTrue(result.isFile());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
