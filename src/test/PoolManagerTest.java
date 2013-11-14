package test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.colman.parser.PoolManager;
import com.colman.parser.XmlParser;
import com.colman.rest.ConfigurationManager;

public class PoolManagerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAttributeValue() {

		Document document;

		final ConfigurationManager instance = ConfigurationManager
		.getInstance();
		instance.setRecreationName("Swimming");
		instance.init();
		
		document = XmlParser.getDom(new File("././assets/resultSample.xml"));

		final NodeList docList = document.getElementsByTagName("doc");

		List<String> day = PoolManager
				.getAttributeValue(docList.item(0), "day");

		assertTrue(day.get(0).equalsIgnoreCase("Monday"));

	}

	@Test
	public void testGetKey() {
		Document document;

		document = XmlParser.getDom(new File("././assets/Day.xml"));

		assertTrue(PoolManager.getNameValuePair("today", document).getKey()
				.equalsIgnoreCase("day_today"));
	}

}
