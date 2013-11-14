package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.colman.parser.XmlParser;

public class XmlParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDomFile() {
		Document dom = XmlParser.getDom(new File("././assets/Day.xml"));
		final NodeList nList = dom.getElementsByTagName("Option");
		assertTrue(nList.getLength()==8);
	}

}
